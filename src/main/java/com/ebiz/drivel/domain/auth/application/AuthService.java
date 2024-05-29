package com.ebiz.drivel.domain.auth.application;

import com.ebiz.drivel.domain.auth.constants.ExceptionMessage;
import com.ebiz.drivel.domain.auth.dto.SignInRequest;
import com.ebiz.drivel.domain.auth.dto.SignInResponse;
import com.ebiz.drivel.domain.auth.dto.SignUpRequest;
import com.ebiz.drivel.domain.auth.exception.DuplicatedSignUpException;
import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

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

    public SignInResponse signIn(SignInRequest request) {
        Authentication authentication = authenticate(request);
        String accessToken = jwtProvider.generateAccessToken(authentication);
        String refreshToken = jwtProvider.generateRefreshToken(authentication);
        return SignInResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private Authentication authenticate(SignInRequest request) {
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        );
        return authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    }
}
