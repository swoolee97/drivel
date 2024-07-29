package com.ebiz.drivel.domain.theme.dto;

import com.ebiz.drivel.domain.course.entity.CourseTheme;
import com.ebiz.drivel.domain.member.entity.MemberTheme;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ThemeDTO {
    private Long id;
    private String displayName;

    public static ThemeDTO from(CourseTheme courseTheme) {
        return ThemeDTO.builder()
                .id(courseTheme.getTheme().getId())
                .displayName(courseTheme.getTheme().getDisplayName())
                .build();
    }

    public static ThemeDTO from(MemberTheme memberTheme) {
        return ThemeDTO.builder()
                .id(memberTheme.getTheme().getId())
                .displayName(memberTheme.getTheme().getDisplayName())
                .build();
    }

}
