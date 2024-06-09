package com.ebiz.drivel.domain.course.dto;

import com.ebiz.drivel.domain.course.entity.Course;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CourseDTO {
    private Long id;
    private String name;
    private String description;
    private String imagePath;

    public static CourseDTO from(Course course) {
        return CourseDTO.builder()
                .id(course.getId())
                .name(course.getName())
                .description(course.getDescription())
                .imagePath(course.getImagePath())
                .build();
    }

}
