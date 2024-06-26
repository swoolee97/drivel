package com.ebiz.drivel.domain.mail.api;

import com.ebiz.drivel.domain.auth.application.AuthService;
import com.ebiz.drivel.domain.mail.dto.CheckCodeDTO;
import com.ebiz.drivel.domain.mail.dto.SendAuthCodeRequest;
import com.ebiz.drivel.domain.mail.service.MailService;
import com.ebiz.drivel.global.dto.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {
    private static final String SEND_AUTH_CODE_MESSAGE = "인증번호가 전송되었습니다";
    private static final String MAIL_AUTH_SUCCESS_MESSAGE = "인증되었습니다";
    private static final String PASSWORD_RESET_SUBJECT = "Drivel 비밀번호 재설정 인증번호";
    private static final String AUTH_MAIL_SUBJECT = "Drivel 가입 인증번호";

    private final AuthService authService;
    private final MailService mailService;

    @PostMapping("/send-reset-code")
    public ResponseEntity<BaseResponse> sendResetCode(@Valid @RequestBody SendAuthCodeRequest sendAuthCodeRequest) {
        mailService.sendAuthenticationCode(PASSWORD_RESET_SUBJECT, sendAuthCodeRequest.getEmail());
        return ResponseEntity.ok(BaseResponse.builder()
                .message(SEND_AUTH_CODE_MESSAGE)
                .build());
    }

    @PostMapping("/auth")
    public ResponseEntity<BaseResponse> sendAuthenticationCode(
            @Valid @RequestBody SendAuthCodeRequest sendAuthCodeRequest) {
        mailService.sendAuthenticationCode(AUTH_MAIL_SUBJECT, sendAuthCodeRequest.getEmail());
        return ResponseEntity.ok(BaseResponse.builder()
                .message(SEND_AUTH_CODE_MESSAGE)
                .build());
    }

    @PostMapping("/check")
    public ResponseEntity<BaseResponse> checkAuthenticationCode(@Valid @RequestBody CheckCodeDTO checkCodeDTO) {
        authService.checkCode(checkCodeDTO);
        return ResponseEntity.ok(BaseResponse.builder()
                .message(MAIL_AUTH_SUCCESS_MESSAGE)
                .build());
    }

}
