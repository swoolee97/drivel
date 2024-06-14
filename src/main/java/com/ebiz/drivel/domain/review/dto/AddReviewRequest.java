package com.ebiz.drivel.domain.review.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
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
    @Size(max = 3) // 최대 3개까지 이미지를 받도록 제한
    private List<MultipartFile> images;
}
