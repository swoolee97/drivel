package com.ebiz.drivel.domain.place.api;

import com.ebiz.drivel.domain.place.dto.PlaceDTO;
import com.ebiz.drivel.domain.place.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/place")
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping("/{id}")
    public ResponseEntity<PlaceDTO> getPlaceDetail(@PathVariable Long id) {
        PlaceDTO placeDTO = placeService.getPlaceDetail(id);
        return ResponseEntity.ok(placeDTO);
    }
}
