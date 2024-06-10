package com.ebiz.drivel.domain.theme.dto;

import com.ebiz.drivel.domain.theme.entity.Theme;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ThemeDTO {
    private Long id;
    private String displayName;

    public static ThemeDTO from(Theme theme) {
        return ThemeDTO.builder()
                .id(theme.getId())
                .displayName(theme.getDisplayName())
                .build();
    }
}
