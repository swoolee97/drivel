package com.ebiz.drivel.domain.festival.api;

import com.ebiz.drivel.domain.festival.exception.FestivalApiException;
import com.ebiz.drivel.domain.festival.service.FestivalApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {FestivalApiService.class})
@Slf4j
public class FestivalExceptionHandler {

    @ExceptionHandler(FestivalApiException.class)
    public void handleFestivalApiException(FestivalApiException e) {
        log.info(e.getMessage());
    }

}
