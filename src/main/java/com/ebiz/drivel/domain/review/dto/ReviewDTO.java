package com.ebiz.drivel.domain.review.dto;

import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.review.entity.CourseReview;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewDTO {
    private Long id;
    private Long reviewerId;
    private String reviewerNickname;
    private String reviewerImagePath;
    private LocalDateTime reviewDate;
    private Long courseId;
    private int rating;
    private String comment;
    private List<ReviewImageDTO> images;

    public static ReviewDTO from(CourseReview courseReview) {
        Member reviewer = courseReview.getMember();
        return ReviewDTO.builder()
                .id(courseReview.getId())
                .reviewerId(reviewer.getId())
                .reviewerNickname(reviewer.getNickname())
                .reviewerImagePath(reviewer.getImagePath())
                .reviewDate(courseReview.getReviewDate())
                .courseId(courseReview.getCourse().getId())
                .rating(courseReview.getRating())
                .comment(courseReview.getComment())
                .images(courseReview.getCourseReviewImages().stream().map(ReviewImageDTO::from)
                        .collect(Collectors.toList()))
                .build();
    }
}
