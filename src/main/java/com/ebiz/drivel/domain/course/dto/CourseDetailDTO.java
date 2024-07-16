package com.ebiz.drivel.domain.course.dto;

import com.ebiz.drivel.domain.course.entity.Course;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class CourseDetailDTO extends CourseDTO {
    private int reviewCount; // 이거랑
    private double averageRating; // 이거는 필요없는데 상속하는게 좋을까

    public static CourseDetailDTO from(Course course, boolean isLiked) {
        return CourseDetailDTO.builder()
                .id(course.getId())
                .liked(isLiked)
                .title(course.getTitle())
                .description(course.getDescription())
                .imagePath(course.getImagePath())
                .build();
    }
}
