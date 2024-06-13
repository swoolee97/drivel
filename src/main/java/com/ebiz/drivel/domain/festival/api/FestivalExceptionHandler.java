package com.ebiz.drivel.domain.festival.api;

import com.ebiz.drivel.domain.festival.exception.FestivalApiException;
import com.ebiz.drivel.domain.festival.exception.FestivalNotFoundException;
import com.ebiz.drivel.global.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {FestivalController.class})
@Slf4j
public class FestivalExceptionHandler {

    @ExceptionHandler(FestivalApiException.class)
    public void handleFestivalApiException(FestivalApiException e) {
        log.info(e.getMessage());
    }

    @ExceptionHandler(FestivalNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleFestivalNotFoundException(FestivalNotFoundException e) {
        return new ResponseEntity(ErrorResponse.builder()
                .message(e.getMessage())
                .build(), HttpStatus.NOT_FOUND);
    }
}
