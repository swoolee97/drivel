package com.ebiz.drivel.domain.course.dto;

import com.ebiz.drivel.domain.course.entity.Course;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class CourseDTO {
    private Long id;
    private boolean liked;
    private String title;
    private String description;
    private String imagePath;

    public static CourseDTO from(Course course, boolean isLiked) {
        return CourseDTO.builder()
                .id(course.getId())
                .liked(isLiked)
                .title(course.getTitle())
                .description(course.getDescription())
                .imagePath(course.getImagePath())
                .build();
    }

    public static CourseDTO from(Course course) {
        return CourseDTO.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .imagePath(course.getImagePath())
                .build();
    }

}
