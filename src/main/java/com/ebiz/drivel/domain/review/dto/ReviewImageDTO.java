package com.ebiz.drivel.domain.review.dto;

import com.ebiz.drivel.domain.review.entity.ReviewImage;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewImageDTO {

    private Long id;
    private String imagePath;

    public static ReviewImageDTO from(ReviewImage reviewImage) {
        return ReviewImageDTO.builder()
                .id(reviewImage.getId())
                .imagePath(reviewImage.getImagePath())
                .build();
    }

}
