package com.ebiz.drivel.domain.mail.api;

import com.ebiz.drivel.domain.mail.exception.InvalidMailException;
import com.ebiz.drivel.domain.mail.exception.WrongAuthenticationCodeException;
import com.ebiz.drivel.global.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {MailController.class})
public class MailExceptionHandler {

    @ExceptionHandler({InvalidMailException.class})
    public ResponseEntity<ErrorResponse> handleInvalidMailException(InvalidMailException e) {
        return ResponseEntity.badRequest()
                .body(ErrorResponse.builder()
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler({WrongAuthenticationCodeException.class})
    public ResponseEntity<ErrorResponse> handleWrongAuthenticationCodeException(WrongAuthenticationCodeException e) {
        return ResponseEntity.badRequest()
                .body(ErrorResponse.builder()
                        .message(e.getMessage())
                        .build());
    }

}
