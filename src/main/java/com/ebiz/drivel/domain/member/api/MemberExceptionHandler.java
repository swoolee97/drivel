package com.ebiz.drivel.domain.member.api;

import com.ebiz.drivel.domain.auth.exception.DuplicatedResourceException;
import com.ebiz.drivel.global.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {MemberController.class})
public class MemberExceptionHandler {
    @ExceptionHandler(DuplicatedResourceException.class)
    public ResponseEntity<?> handleDuplicatedResourceExceptionHandler(DuplicatedResourceException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
