package com.ebiz.drivel.domain.festival.api;

import com.ebiz.drivel.domain.festival.service.FestivalApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/festival")
@RequiredArgsConstructor
public class FestivalController {

    private final FestivalApiService festivalApiService;

    @GetMapping("/test")
    public void abc() throws Exception {
        festivalApiService.updateFestivalData();
    }

}
