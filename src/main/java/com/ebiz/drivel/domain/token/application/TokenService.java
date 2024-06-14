package com.ebiz.drivel.domain.token.application;

import com.ebiz.drivel.domain.auth.application.JwtProvider;
import com.ebiz.drivel.domain.auth.application.UserDetailsServiceImpl;
import com.ebiz.drivel.domain.auth.dto.SignInDTO;
import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.token.exception.DifferentRefreshTokenException;
import com.ebiz.drivel.domain.token.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class TokenService {
    private static final String DIFFERENT_REFRESH_TOKEN_EXCEPTION_MESSAGE = "다시 로그인 해주세요";

    private final JwtProvider jwtProvider;
    private final UserDetailsServiceImpl userDetailsService;
    private final TokenRepository tokenRepository;

    public SignInDTO generateTokens() {
        Authentication authentication = userDetailsService.getAuthentication();
        Member member = userDetailsService.getMemberByContextHolder();
        String accessToken = jwtProvider.generateAccessToken(authentication);
        String refreshToken = jwtProvider.generateRefreshToken(authentication);
        tokenRepository.save(member.getId(), refreshToken);
        return SignInDTO.builder()
                .nickname(member.getNickname())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void checkRefreshToken(String refreshToken) {
        Member member = userDetailsService.getMemberByContextHolder();
        String token = resolveToken(refreshToken);
        String refreshTokenInRedis = tokenRepository.findById(member.getId());
        if (refreshTokenInRedis == null || !refreshTokenInRedis.equals(token)) {
            deleteRefreshToken(member.getId());
            throw new DifferentRefreshTokenException(DIFFERENT_REFRESH_TOKEN_EXCEPTION_MESSAGE);
        }
    }

    public void deleteRefreshToken(Long memberId) {
        tokenRepository.deleteById(memberId);
    }

    public String resolveToken(String tokenHeader) {
        if (StringUtils.hasText(tokenHeader) && tokenHeader.startsWith("Bearer")) {
            return tokenHeader.substring(7);
        }
        return null;
    }

}
