package com.ebiz.drivel.domain.review.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AddReviewDTO {
    @NotNull
    private Long courseId;
    @NotNull
    private int rating;
    @NotNull
    private String comment;
}
