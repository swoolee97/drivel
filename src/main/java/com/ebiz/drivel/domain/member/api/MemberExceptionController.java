package com.ebiz.drivel.domain.member.api;

import com.ebiz.drivel.domain.member.exception.MemberNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {MemberController.class})
public class MemberExceptionController {

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMemberNotFoundException(
            MemberNotFoundException memberNotFoundException){
        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                .message(memberNotFoundException.getMessage())
                .build());
    }
}
