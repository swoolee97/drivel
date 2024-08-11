package com.ebiz.drivel.domain.course.dto;

import com.ebiz.drivel.domain.onboarding.entity.Tag;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CourseTagDTO {
    private Long tagId;
    private String tagName;
    private List<CourseDTO> courses;

    public static CourseTagDTO from(List<CourseDTO> courses, Tag tag) {
        return CourseTagDTO.builder()
                .tagId(tag.getId())
                .tagName(tag.getDisplayName())
                .courses(courses)
                .build();
    }

}
