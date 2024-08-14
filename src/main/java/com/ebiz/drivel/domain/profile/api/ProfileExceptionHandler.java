package com.ebiz.drivel.domain.profile.api;

import com.ebiz.drivel.domain.profile.exception.ProfileException;
import com.ebiz.drivel.global.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {ProfileController.class})
public class ProfileExceptionHandler {

    @ExceptionHandler(ProfileException.class)
    public ResponseEntity<ErrorResponse> handleProfileException(ProfileException profileException) {
        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                .message(profileException.getMessage())
                .build());
    }
}
