package com.ebiz.drivel.domain.theme.service;

import com.ebiz.drivel.domain.theme.entity.Theme;
import com.ebiz.drivel.domain.theme.repository.ThemeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThemeService {

    private final ThemeRepository themeRepository;

    public List<Theme> getAllThemes() {
        return themeRepository.findAll();
    }
}
