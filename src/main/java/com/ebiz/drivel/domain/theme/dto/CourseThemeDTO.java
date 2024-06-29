package com.ebiz.drivel.domain.theme.dto;

import com.ebiz.drivel.domain.course.dto.CourseDTO;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CourseThemeDTO {
    private ThemeDTO theme;
    private List<CourseDTO> courses;
}
