package com.ebiz.drivel.auth.api;

import com.ebiz.drivel.auth.application.AuthService;
import com.ebiz.drivel.auth.dto.SignInRequest;
import com.ebiz.drivel.auth.dto.SignInResponse;
import com.ebiz.drivel.auth.dto.SignUpRequest;
import com.ebiz.drivel.member.entity.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signUp")
    public void signUp(@Valid @RequestBody SignUpRequest request) {
        Member member = authService.signUp(request);
    }

    @PostMapping("/signIn")
    public void signIn(@Valid @RequestBody SignInRequest request) {
        SignInResponse response = authService.signIn(request);
    }

}
