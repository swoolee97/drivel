package com.ebiz.drivel.domain.auth.api;

import com.ebiz.drivel.domain.auth.exception.DuplicatedSignUpException;
import com.ebiz.drivel.global.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {AuthController.class})
public class AuthExceptionHandler {

    @ExceptionHandler(DuplicatedSignUpException.class)
    public ResponseEntity<?> handleDuplicatedSignUpHandler(DuplicatedSignUpException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
