package com.ebiz.drivel.domain.magazine.controller;

import com.ebiz.drivel.domain.magazine.entity.Magazine;
import com.ebiz.drivel.domain.magazine.service.MagazineService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/magazine")
public class MagazineController {

    private final MagazineService magazineService;

    @GetMapping
    public ResponseEntity<List<Magazine>> getRandomMagazine() {
        List<Magazine> magazines = magazineService.getRandomMagazine();
        return ResponseEntity.ok(magazines);
    }
}
