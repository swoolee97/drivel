package com.ebiz.drivel.domain.review.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class AddReviewRequest {
    @NotNull
    private Long courseId;

    @NotNull
    private int rating;

    @NotNull
    @Size(min = 10)
    private String comment;

    @Setter
    private MultipartFile image;
}
