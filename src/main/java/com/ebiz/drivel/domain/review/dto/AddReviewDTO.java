package com.ebiz.drivel.domain.review.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class AddReviewDTO {
    @NotNull
    private Long courseId;

    @NotNull
    private int rating;

    @NotNull
    @Size(min = 10)
    private String comment;
}
