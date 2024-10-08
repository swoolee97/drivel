package com.ebiz.drivel.domain.review.api;

import com.ebiz.drivel.domain.review.dto.AddReviewRequest;
import com.ebiz.drivel.domain.review.dto.ReviewDTO;
import com.ebiz.drivel.domain.review.dto.ReviewResponse;
import com.ebiz.drivel.domain.review.entity.CourseReview;
import com.ebiz.drivel.domain.review.entity.CourseReviewImage;
import com.ebiz.drivel.domain.review.exception.MaxImageLengthExceededException;
import com.ebiz.drivel.domain.review.service.ReviewImageService;
import com.ebiz.drivel.domain.review.service.ReviewService;
import com.ebiz.drivel.global.dto.BaseResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private static final String ADD_REVIEW_SUCCESS_MESSAGE = "리뷰가 등록되었습니다";
    private static final String EXCEEDED_IMAGE_MAX_LENGTH_EXCEPTION_MESSAGE = "이미지는 3개까지 등록할 수 있습니다";
    private static final String DELETE_REVIEW_SUCCESS_MESSAGE = "리뷰가 삭제되었습니다";
    private static final int IMAGES_MAX_LENGTH = 3;

    private final ReviewService reviewService;
    private final ReviewImageService reviewImageService;

    @PostMapping("/add")
    public ResponseEntity<BaseResponse> addReview(AddReviewRequest addReviewRequest) {
        List<MultipartFile> images = addReviewRequest.getImages();

        if (images != null && images.size() > IMAGES_MAX_LENGTH) {
            throw new MaxImageLengthExceededException(EXCEEDED_IMAGE_MAX_LENGTH_EXCEPTION_MESSAGE);
        }

        CourseReview courseReview = reviewService.addReview(
                addReviewRequest.getCourseId(), addReviewRequest.getRating(), addReviewRequest.getComment());
        List<CourseReviewImage> courseReviewImages = reviewImageService.addReviewImages(courseReview, images);
        return ResponseEntity.ok(BaseResponse.builder()
                .message(ADD_REVIEW_SUCCESS_MESSAGE)
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deleteReview(@PathVariable("id") Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok(BaseResponse.builder()
                .message(DELETE_REVIEW_SUCCESS_MESSAGE)
                .build());
    }

    @GetMapping("/my")
    public ResponseEntity<ReviewResponse> findMyReviews() {
        List<ReviewDTO> myReviews = reviewService.findMyReviews();
        return ResponseEntity.ok(ReviewResponse.builder()
                .reviews(myReviews)
                .build());
    }

}
