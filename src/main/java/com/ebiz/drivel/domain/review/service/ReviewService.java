package com.ebiz.drivel.domain.review.service;

import com.ebiz.drivel.domain.auth.application.UserDetailsServiceImpl;
import com.ebiz.drivel.domain.course.entity.Course;
import com.ebiz.drivel.domain.course.repository.CourseRepository;
import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.review.dto.AddReviewDTO;
import com.ebiz.drivel.domain.review.dto.ReviewDTO;
import com.ebiz.drivel.domain.review.entity.Review;
import com.ebiz.drivel.domain.review.repository.ReviewRepository;
import com.ebiz.drivel.global.exception.CourseNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserDetailsServiceImpl userDetailsService;
    private final ReviewRepository reviewRepository;
    private final CourseRepository courseRepository;

    public Review addReview(AddReviewDTO addReviewDTO) {
        Course course = courseRepository.findById(addReviewDTO.getCourseId())
                .orElseThrow(() -> new CourseNotFoundException());

        Review review = Review.builder()
                .comment(addReviewDTO.getComment())
                .rating(addReviewDTO.getRating())
                .course(course)
                .member(userDetailsService.getMemberByContextHolder())
                .build();
        return reviewRepository.save(review);
    }

    public List<ReviewDTO> findMyReviews() {
        Member member = userDetailsService.getMemberByContextHolder();
        List<Review> myReviews = reviewRepository.findAllByMemberId(member.getId());
        return myReviews.stream().map(ReviewDTO::from).collect(Collectors.toList());
    }

}
