package com.ebiz.drivel.domain.review.api;

import com.ebiz.drivel.domain.review.constants.ReviewExceptionMessage;
import com.ebiz.drivel.global.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {ReviewController.class})
public class ReviewExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleShortCommentExceptionHandler() {
        return new ResponseEntity<>(ErrorResponse.builder()
                .message(ReviewExceptionMessage.SHORT_REVIEW_COMMENT_EXCEPTION_MESSAGE)
                .build(), HttpStatus.BAD_REQUEST);
    }
}
