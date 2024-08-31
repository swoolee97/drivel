package com.ebiz.drivel.domain.token.api;

import com.ebiz.drivel.domain.auth.application.UserDetailsServiceImpl;
import com.ebiz.drivel.domain.auth.dto.SignInDTO;
import com.ebiz.drivel.domain.auth.exception.SignInDeletedMemberException;
import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.token.application.TokenService;
import com.ebiz.drivel.global.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
public class TokenController {
    private static final String AUTO_SIGN_IN_SUCCESS_MESSAGE = "자동 로그인 성공";

    private final TokenService tokenService;
    private final UserDetailsServiceImpl userDetailsService;

    @PostMapping("/signIn")
    public ResponseEntity<BaseResponse> checkToken() {
        Member member = userDetailsService.getMemberByContextHolder();
        if (member.isDeleted()) {
            throw new SignInDeletedMemberException("탈퇴한 회원입니다");
        }
        return ResponseEntity.ok(SignInDTO.builder()
                .id(member.getId())
                .message(AUTO_SIGN_IN_SUCCESS_MESSAGE)
                .nickname(member.getNickname())
                .onboarded(member.isOnboarded())
                .build());
    }

    @PostMapping("/re-issue")
    public ResponseEntity<BaseResponse> reIssueToken(@RequestHeader("Authorization") String authorizationHeader) {
        tokenService.checkRefreshToken(authorizationHeader);
        SignInDTO signInDTO = tokenService.generateTokens();
        return ResponseEntity.ok(signInDTO);
    }

}
