package com.ebiz.drivel.domain.festival.api;

import com.ebiz.drivel.domain.festival.dto.FestivalDetailResponse;
import com.ebiz.drivel.domain.festival.service.FestivalApiService;
import com.ebiz.drivel.domain.festival.service.FestivalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/festival")
@RequiredArgsConstructor
public class FestivalController {

    private final FestivalApiService festivalApiService;
    private final FestivalService festivalService;

    @GetMapping("/test")
    public void abc() throws Exception {
        festivalApiService.updateFestivalData();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FestivalDetailResponse> getFestivalDetail(@PathVariable String id) {
        FestivalDetailResponse festivalDetailResponse = festivalService.getFestivalInfo(id);
        return ResponseEntity.ok(festivalDetailResponse);
    }

}
