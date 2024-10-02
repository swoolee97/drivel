package com.ebiz.drivel.domain.apple.service;

import com.ebiz.drivel.domain.auth.application.JwtProvider;
import com.ebiz.drivel.domain.auth.dto.SignInDTO;
import com.ebiz.drivel.domain.auth.exception.SignInDeletedMemberException;
import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.member.repository.MemberRepository;
import com.ebiz.drivel.domain.member.util.NicknameGenerator;
import com.ebiz.drivel.domain.member.util.ProfileImageGenerator;
import com.ebiz.drivel.domain.push.entity.FcmToken;
import com.ebiz.drivel.domain.push.repository.FcmTokenRepository;
import com.ebiz.drivel.domain.token.repository.TokenRepository;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppleService {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final TokenRepository tokenRepository;
    private final ProfileImageGenerator profileImageGenerator;
    private final NicknameGenerator nicknameGenerator;
    private final FcmTokenRepository fcmTokenRepository;

    public SignInDTO signInWithApple(String email, String token) {
        Member member = memberRepository.findMemberByEmail(email).orElse(null);
        if (member == null) {
            member = signUpWithApple(email);
        }
        if (member.isDeleted()) {
            throw new SignInDeletedMemberException("탈퇴한 회원입니다");
        }

        Authentication authentication = authenticate(email);

        String accessToken = jwtProvider.generateAccessToken(authentication);
        String refreshToken = jwtProvider.generateRefreshToken(authentication);
        tokenRepository.save(member.getId(), refreshToken);

        fcmTokenRepository.deleteAllByMemberId(member.getId());
        FcmToken fcmToken = FcmToken.builder()
                .memberId(member.getId())
                .token(token)
                .build();
        fcmTokenRepository.save(fcmToken);

        return SignInDTO.builder()
                .id(member.getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .nickname(member.getNickname())
                .onboarded(member.isOnboarded())
                .build();
    }

    public Member signUpWithApple(String email) {
        return memberRepository.save(Member.builder()
                .email(email)
                .nickname(nicknameGenerator.generateUniqueNickname())
                .imagePath(profileImageGenerator.getDefaultProfileImagePath())
                .build());
    }


    private Authentication authenticate(String email) {
        User user = new User(email, "", Collections.singletonList(new SimpleGrantedAuthority("USER")));
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

}
