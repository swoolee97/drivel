package com.ebiz.drivel.domain.course.dto;

import com.ebiz.drivel.domain.course.entity.Course;
import com.ebiz.drivel.domain.course.entity.CourseStyle;
import com.ebiz.drivel.domain.course.entity.CourseTheme;
import com.ebiz.drivel.domain.course.entity.CourseTogether;
import com.ebiz.drivel.domain.onboarding.entity.Style;
import com.ebiz.drivel.domain.onboarding.entity.Together;
import com.ebiz.drivel.domain.theme.entity.Theme;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class CourseDetailDTO extends CourseDTO {
    private int reviewCount; // 이거랑
    private double averageRating; // 이거는 필요없는데 상속하는게 좋을까
    private String region;
    private List<String> tags;

    public static CourseDetailDTO from(Course course, boolean isLiked) {
        List<String> tags = new ArrayList<>();
        tags.addAll(course.getCourseStyles().stream().map(CourseStyle::getStyle).map(Style::getDisplayName).toList());
        tags.addAll(course.getCourseThemes().stream().map(CourseTheme::getTheme).map(Theme::getDisplayName).toList());
        tags.addAll(course.getCourseTogethers().stream().map(CourseTogether::getTogether).map(Together::getDisplayName)
                .toList());

        return CourseDetailDTO.builder()
                .id(course.getId())
                .liked(isLiked)
                .tags(tags)
                .region(course.getRegionName())
                .title(course.getTitle())
                .description(course.getDescription())
                .reviewCount(course.countReviews())
                .averageRating(course.calculateAverageRating())
                .imagePath(course.getImagePath())
                .build();
    }
}
