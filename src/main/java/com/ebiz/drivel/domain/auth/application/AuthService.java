package com.ebiz.drivel.domain.auth.application;

import static com.ebiz.drivel.domain.member.exception.MemberExceptionMessage.MEMBER_NOT_FOUND_EXCEPTION_MESSAGE;

import com.ebiz.drivel.domain.auth.constants.ExceptionMessage;
import com.ebiz.drivel.domain.auth.dto.SignInDTO;
import com.ebiz.drivel.domain.auth.dto.SignInRequest;
import com.ebiz.drivel.domain.auth.dto.SignUpRequest;
import com.ebiz.drivel.domain.auth.exception.DuplicatedSignUpException;
import com.ebiz.drivel.domain.mail.dto.CheckCodeDTO;
import com.ebiz.drivel.domain.mail.exception.WrongAuthenticationCodeException;
import com.ebiz.drivel.domain.mail.repository.AuthCodeRepository;
import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.member.exception.MemberNotFoundException;
import com.ebiz.drivel.domain.member.repository.MemberRepository;
import com.ebiz.drivel.domain.token.application.TokenService;
import com.ebiz.drivel.domain.token.entity.BlackList;
import com.ebiz.drivel.domain.token.repository.BlackListRepository;
import com.ebiz.drivel.domain.token.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private static final String WRONG_CODE_EXCEPTION_MESSAGE = "인증번호가 다릅니다";

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtProvider jwtProvider;
    private final TokenService tokenService;
    private final MemberRepository memberRepository;
    private final TokenRepository tokenRepository;
    private final AuthCodeRepository authCodeRepository;
    private final PasswordEncoder encoder;
    private final BlackListRepository blackListRepository;

    @Transactional
    public Member signUp(SignUpRequest request) {
        try {
            Member member = Member.builder()
                    .email(request.getEmail())
                    .password(encoder.encode(request.getPassword()))
                    .nickname(request.getNickname())
                    .build();
            return memberRepository.save(member);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicatedSignUpException(ExceptionMessage.DUPLICATED_SIGN_UP);
        }
    }

    public SignInDTO signIn(SignInRequest request) {
        Authentication authentication = authenticate(request);
        String accessToken = jwtProvider.generateAccessToken(authentication);
        String refreshToken = jwtProvider.generateRefreshToken(authentication);
        Member member = memberRepository.findMemberByEmail(request.getEmail())
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND_EXCEPTION_MESSAGE));
        tokenRepository.save(member.getId(), refreshToken);
        return SignInDTO.builder()
                .nickname(member.getNickname())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public void signOut(Member member, String authorizationHeader) {
        String storedToken = tokenRepository.findById(member.getId());
        String refreshToken = tokenService.resolveToken(authorizationHeader);
        tokenRepository.delete(member.getId());
        saveBlackList(refreshToken);
        if (!refreshToken.equals(storedToken)) {
            saveBlackList(storedToken);
        }
    }

    private void saveBlackList(String refreshToken) {
        try {
            blackListRepository.save(BlackList.builder()
                    .refresh_token(refreshToken)
                    .build());
        } catch (DuplicateKeyException e) {
            log.info("이미 로그아웃 처리한 토큰: " + refreshToken);
        }
    }

    public void checkCode(CheckCodeDTO checkCodeDTO) {
        String storedCode = authCodeRepository.findCodeByEmailAndRandomCode(checkCodeDTO);
        authCodeRepository.delete(checkCodeDTO.getEmail());
        if (storedCode == null || !checkCodeDTO.getCode().equals(storedCode.toString())) {
            throw new WrongAuthenticationCodeException(WRONG_CODE_EXCEPTION_MESSAGE);
        }
    }

    private Authentication authenticate(SignInRequest request) {
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        );
        return authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    }
}
