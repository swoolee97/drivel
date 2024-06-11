package com.ebiz.drivel.domain.theme.api;

import com.ebiz.drivel.domain.theme.dto.ThemeDTO;
import com.ebiz.drivel.domain.theme.dto.ThemeResponse;
import com.ebiz.drivel.domain.theme.service.ThemeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/theme")
public class ThemeController {

    private final ThemeService themeService;

    @GetMapping("/all")
    public ResponseEntity<ThemeResponse> getAllThemes() {
        List<ThemeDTO> themes = themeService.getAllThemes().stream().map(ThemeDTO::from).toList();
        return ResponseEntity.ok(ThemeResponse.builder()
                .themes(themes)
                .build());
    }

}
