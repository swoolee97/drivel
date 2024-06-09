package com.ebiz.drivel.domain.token.api;

import com.ebiz.drivel.domain.auth.dto.SignInDTO;
import com.ebiz.drivel.domain.token.application.TokenService;
import com.ebiz.drivel.global.dto.SuccessResponse;
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

    @PostMapping("/signIn")
    public ResponseEntity<SuccessResponse> checkToken() {
        SignInDTO signInDTO = tokenService.generateTokens();
        return ResponseEntity.ok(SuccessResponse.builder()
                .data(signInDTO)
                .message(AUTO_SIGN_IN_SUCCESS_MESSAGE)
                .build());
    }

    @PostMapping("/re-issue")
    public ResponseEntity<SuccessResponse> reIssueToken(@RequestHeader("Authorization") String refreshToken) {
        tokenService.checkRefreshToken(refreshToken);
        SignInDTO signInDTO = tokenService.generateTokens();
        return ResponseEntity.ok(SuccessResponse.builder()
                .data(signInDTO)
                .message(AUTO_SIGN_IN_SUCCESS_MESSAGE)
                .build());
    }

}
