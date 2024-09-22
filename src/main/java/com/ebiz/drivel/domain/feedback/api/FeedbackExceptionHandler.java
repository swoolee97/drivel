package com.ebiz.drivel.domain.feedback.api;

import com.ebiz.drivel.domain.feedback.exception.DuplicateFeedbackException;
import com.ebiz.drivel.domain.feedback.exception.EmptyFeedbackException;
import com.ebiz.drivel.global.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {FeedbackController.class})
public class FeedbackExceptionHandler {

    @ExceptionHandler(EmptyFeedbackException.class)
    public ResponseEntity<ErrorResponse> handleEmptyFeedbackException(EmptyFeedbackException e) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .message(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateFeedbackException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatedFeedbackException(DuplicateFeedbackException e) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .message(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

}
