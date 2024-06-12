package com.ebiz.drivel.domain.festival;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/festival")
@RequiredArgsConstructor
public class FestivalController {

    private final FestivalService festivalService;

    @GetMapping("/test")
    public void abc() throws Exception {
        festivalService.updateFestivalData();
    }

}
