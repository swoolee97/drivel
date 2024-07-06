package com.ebiz.drivel.domain.kakao.service;

import com.ebiz.drivel.domain.auth.application.JwtProvider;
import com.ebiz.drivel.domain.auth.dto.SignInDTO;
import com.ebiz.drivel.domain.kakao.dto.KakaoTokenResponse;
import com.ebiz.drivel.domain.kakao.dto.KakaoUserInfoResponse;
import com.ebiz.drivel.domain.kakao.exception.DuplicatedSignUpMemberException;
import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.member.repository.MemberRepository;
import com.ebiz.drivel.domain.member.util.NicknameGenerator;
import com.ebiz.drivel.domain.member.util.ProfileImageGenerator;
import com.ebiz.drivel.domain.token.repository.TokenRepository;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class KakaoService {
    private static final String DUPLICATED_SIGN_UP_EXCEPTION_MESSAGE = "이미 가입된 회원입니다";
    private static final String TOKEN_URI = "https://kauth.kakao.com/oauth/token";
    private static final String GRANT_TYPE = "authorization_code";
    private static final String USER_INFO_URI = "https://kapi.kakao.com/v2/user/me";

    @Value("${KAKAO_RESTAPI_KEY}")
    private String CLIENT_ID;
    @Value("${KAKAO_REDIRECT_URI}")
    private String REDIRECT_URI;
    private final WebClient webClient;
    private final MemberRepository memberRepository;
    private final NicknameGenerator nicknameGenerator;
    private final JwtProvider jwtProvider;
    private final TokenRepository tokenRepository;
    private final ProfileImageGenerator profileImageGenerator;

    @Transactional
    public SignInDTO loginWithKakao(String email) {
        Member member = memberRepository.findMemberByEmail(email).orElse(null);
        if (member != null && member.getPassword() != null) {
            throw new DuplicatedSignUpMemberException(DUPLICATED_SIGN_UP_EXCEPTION_MESSAGE);
        }
        if (member == null) {
            member = signUpWithKakao(email);
        }
        Authentication authentication = authenticate(email);

        String accessToken = jwtProvider.generateAccessToken(authentication);
        String refreshToken = jwtProvider.generateRefreshToken(authentication);
        tokenRepository.save(member.getId(), refreshToken);

        return SignInDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .nickname(member.getNickname())
                .build();
    }

    public Member signUpWithKakao(String email) {
        return memberRepository.save(Member.builder()
                .email(email)
                .nickname(nicknameGenerator.generateUniqueNickname())
                .imagePath(profileImageGenerator.getDefaultProfileImagePath())
                .build());
    }

    public KakaoTokenResponse getToken(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", GRANT_TYPE);
        params.add("redirect_uri", REDIRECT_URI);
        params.add("client_id", CLIENT_ID);
        params.add("code", code);

        Flux<KakaoTokenResponse> response = webClient.post()
                .uri(TOKEN_URI)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(params))
                .retrieve()
                .bodyToFlux(KakaoTokenResponse.class);

        return response.blockFirst();
    }

    public KakaoUserInfoResponse getUserInfo(String token) {

        Flux<KakaoUserInfoResponse> response = webClient.get()
                .uri(USER_INFO_URI)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToFlux(KakaoUserInfoResponse.class);

        return response.blockFirst();
    }

    private Authentication authenticate(String email) {
        User user = new User(email, "", Collections.singletonList(new SimpleGrantedAuthority("USER")));
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

}
