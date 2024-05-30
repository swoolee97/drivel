package com.ebiz.drivel.domain.auth.api;

import com.ebiz.drivel.domain.auth.application.AuthService;
import com.ebiz.drivel.domain.auth.dto.SignInDTO;
import com.ebiz.drivel.domain.auth.dto.SignInRequest;
import com.ebiz.drivel.domain.auth.dto.SignUpRequest;
import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.global.dto.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private static final String SIGN_IN_SUCCESS_MESSAGE = "로그인 성공";

    private final AuthService authService;

    @PostMapping("/signUp")
    public void signUp(@Valid @RequestBody SignUpRequest request) {
        Member member = authService.signUp(request);
    }

    @PostMapping("/signIn")
    public ResponseEntity<SuccessResponse> signIn(@Valid @RequestBody SignInRequest request) {
        SignInDTO signInDTO = authService.signIn(request);
        return new ResponseEntity<>(SuccessResponse.builder()
                .message(SIGN_IN_SUCCESS_MESSAGE)
                .data(signInDTO).build(), HttpStatus.OK);
    }

}
