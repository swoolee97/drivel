package com.ebiz.drivel.domain.auth.api;

import com.ebiz.drivel.domain.auth.application.AuthService;
import com.ebiz.drivel.domain.auth.dto.ResetCodeRequest;
import com.ebiz.drivel.domain.auth.dto.SignInDTO;
import com.ebiz.drivel.domain.auth.dto.SignInRequest;
import com.ebiz.drivel.domain.auth.dto.SignUpRequest;
import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.global.dto.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private static final String SIGN_IN_SUCCESS_MESSAGE = "로그인 성공";
    private static final String SIGN_UP_SUCCESS_MESSAGE = "회원가입 성공";

    private final AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<BaseResponse> signUp(@Valid @RequestBody SignUpRequest request) {
        Member member = authService.signUp(request);
        return new ResponseEntity<>(BaseResponse.builder()
                .message(SIGN_UP_SUCCESS_MESSAGE)
                .build(), HttpStatus.OK);
    }

    @PostMapping("/signIn")
    public ResponseEntity<SignInDTO> signIn(@Valid @RequestBody SignInRequest request) {
        SignInDTO signInDTO = authService.signIn(request);
        return new ResponseEntity<>(signInDTO, HttpStatus.OK);
    }

    /*
    signOut할 때 fcmtoken도 삭제하는 로직 추가해야함.(푸시알림 기능 추가한 이후에)
     */
    @PostMapping("/signOut")
    public void signOut(@RequestHeader(name = "Authorization") String authorizationHeader) {
        authService.signOut(authorizationHeader);
    }

    // 4개
    // GET POST DELETE PUT
    @PostMapping("/send-reset-code")
    public void sendResetCode(@RequestBody ResetCodeRequest request) {

    }

}
