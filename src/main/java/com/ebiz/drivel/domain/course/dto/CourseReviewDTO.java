package com.ebiz.drivel.domain.course.dto;

import com.ebiz.drivel.domain.review.dto.ReviewDTO;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@Builder
public class CourseReviewDTO {
    private double averageRating;
    private Page<ReviewDTO> reviews;
}
