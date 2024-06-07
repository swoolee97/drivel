package com.ebiz.drivel.domain.review.service;

import com.ebiz.drivel.domain.auth.application.UserDetailsServiceImpl;
import com.ebiz.drivel.domain.review.dto.AddReviewDTO;
import com.ebiz.drivel.domain.review.entity.Review;
import com.ebiz.drivel.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserDetailsServiceImpl userDetailsService;
    private final ReviewRepository reviewRepository;

    public Review addReview(AddReviewDTO addReviewDTO) {
        Review review = Review.builder()
                .comment(addReviewDTO.getComment())
                .rating(addReviewDTO.getRating())
                .courseId(addReviewDTO.getCourseId())
                .member(userDetailsService.getMemberByContextHolder())
                .build();
        return reviewRepository.save(review);
    }

}
