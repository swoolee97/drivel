package com.ebiz.drivel.domain.review.dto;

import com.ebiz.drivel.domain.review.entity.Review;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewDTO {
    private Long id;
    private String reviewerNickname;
    private String reviewerImagePath;
    private LocalDateTime reviewDate;
    private Long courseId;
    private int rating;
    private String comment;
    private List<ReviewImageDTO> images;

    public static ReviewDTO from(Review review) {
        return ReviewDTO.builder()
                .id(review.getId())
                .reviewerNickname(review.getMember().getNickname())
                .reviewerImagePath(review.getMember().getImagePath())
                .reviewDate(review.getReviewDate())
                .courseId(review.getCourse().getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .images(review.getReviewImages().stream().map(ReviewImageDTO::from).collect(Collectors.toList()))
                .build();
    }
}
