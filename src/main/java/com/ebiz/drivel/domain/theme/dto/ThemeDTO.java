package com.ebiz.drivel.domain.theme.dto;

import com.ebiz.drivel.domain.course.entity.CourseTheme;
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

    public static ThemeDTO from(CourseTheme courseTheme) {
        return ThemeDTO.builder()
                .id(courseTheme.getId())
                .displayName(courseTheme.getTheme().getDisplayName())
                .build();
    }

}
