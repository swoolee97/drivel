package com.ebiz.drivel.domain.review.dto;

import com.ebiz.drivel.domain.review.entity.CourseReviewImage;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewImageDTO {

    private Long id;
    private String imagePath;

    public static ReviewImageDTO from(CourseReviewImage courseReviewImage) {
        return ReviewImageDTO.builder()
                .id(courseReviewImage.getId())
                .imagePath(courseReviewImage.getImagePath())
                .build();
    }

}
