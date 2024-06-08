package com.ebiz.drivel.domain.review.service;

import com.ebiz.drivel.domain.auth.application.UserDetailsServiceImpl;
import com.ebiz.drivel.domain.course.entity.Course;
import com.ebiz.drivel.domain.course.repository.CourseRepository;
import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.review.dto.AddReviewRequest;
import com.ebiz.drivel.domain.review.dto.ReviewDTO;
import com.ebiz.drivel.domain.review.entity.Review;
import com.ebiz.drivel.domain.review.repository.ReviewRepository;
import com.ebiz.drivel.global.exception.CourseNotFoundException;
import com.ebiz.drivel.global.service.S3Service;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserDetailsServiceImpl userDetailsService;
    private final ReviewRepository reviewRepository;
    private final CourseRepository courseRepository;
    private final S3Service s3Service;
    @Value("${cloud.aws.s3.reviewImageBucketName}")
    private String REVIEW_IMAGE_BUCKET_NAME;

    public Review addReview(AddReviewRequest addReviewRequest) throws IOException {
        Course course = courseRepository.findById(addReviewRequest.getCourseId())
                .orElseThrow(() -> new CourseNotFoundException());
        String imagePath = null;
        if (addReviewRequest.getImage() != null) {
            imagePath = s3Service.uploadImageFile(addReviewRequest.getImage(), REVIEW_IMAGE_BUCKET_NAME);
        }

        Review review = Review.builder()
                .comment(addReviewRequest.getComment())
                .rating(addReviewRequest.getRating())
                .course(course)
                .member(userDetailsService.getMemberByContextHolder())
                .imagePath(imagePath)
                .build();
        return reviewRepository.save(review);
    }

    public List<ReviewDTO> findMyReviews() {
        Member member = userDetailsService.getMemberByContextHolder();
        List<Review> myReviews = reviewRepository.findAllByMemberId(member.getId());
        return myReviews.stream().map(ReviewDTO::from).collect(Collectors.toList());
    }

}
