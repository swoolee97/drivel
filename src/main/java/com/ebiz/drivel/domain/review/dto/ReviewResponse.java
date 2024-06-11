package com.ebiz.drivel.domain.review.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewResponse {
    List<ReviewDTO> reviews;
}
