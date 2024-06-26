package com.ebiz.drivel.domain.review.service;

import com.ebiz.drivel.domain.auth.application.UserDetailsServiceImpl;
import com.ebiz.drivel.domain.course.entity.Course;
import com.ebiz.drivel.domain.course.repository.CourseRepository;
import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.review.dto.AddReviewRequest;
import com.ebiz.drivel.domain.review.dto.ReviewDTO;
import com.ebiz.drivel.domain.review.entity.CourseReview;
import com.ebiz.drivel.domain.review.repository.ReviewRepository;
import com.ebiz.drivel.global.exception.CourseNotFoundException;
import java.util.Comparator;
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

    public CourseReview addReview(AddReviewRequest addReviewRequest) {
        Course course = courseRepository.findById(addReviewRequest.getCourseId())
                .orElseThrow(() -> new CourseNotFoundException());
        CourseReview courseReview = CourseReview.builder()
                .comment(addReviewRequest.getComment())
                .rating(addReviewRequest.getRating())
                .course(course)
                .member(userDetailsService.getMemberByContextHolder())
                .build();
        return reviewRepository.save(courseReview);
    }

    public List<ReviewDTO> findMyReviews() {
        Member member = userDetailsService.getMemberByContextHolder();
        List<CourseReview> myCourseReviews = member.getCourseReviews();
        return myCourseReviews.stream()
                .map(ReviewDTO::from)
                .sorted(Comparator.comparingLong(ReviewDTO::getId).reversed())
                .collect(Collectors.toList());
    }

}
