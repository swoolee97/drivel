package com.ebiz.drivel.domain.kakao.api;

import com.ebiz.drivel.domain.kakao.exception.DuplicatedSignUpMemberException;
import com.ebiz.drivel.global.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {KakaoController.class})
public class KakaoExceptionHandler {

    @ExceptionHandler({DuplicatedSignUpMemberException.class})
    public ResponseEntity<ErrorResponse> handleDuplicatedSignUpMemberException(DuplicatedSignUpMemberException e) {
        return ResponseEntity.badRequest()
                .body(ErrorResponse.builder()
                        .message(e.getMessage())
                        .build());
    }
}
