package com.ebiz.drivel.domain.review.api;

import com.ebiz.drivel.domain.review.dto.AddReviewRequest;
import com.ebiz.drivel.domain.review.dto.ReviewDTO;
import com.ebiz.drivel.domain.review.dto.ReviewResponse;
import com.ebiz.drivel.domain.review.entity.Review;
import com.ebiz.drivel.domain.review.service.ReviewService;
import com.ebiz.drivel.global.dto.BaseResponse;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private static final String ADD_REVIEW_SUCCESS_MESSAGE = "리뷰가 등록되었습니다";

    private final ReviewService reviewService;

    @PostMapping("/add")
    public ResponseEntity<BaseResponse> addReview(
            @Valid @RequestPart("review") AddReviewRequest addReviewRequest,
            @Nullable @RequestPart("image") MultipartFile image) throws IOException {
        addReviewRequest.setImage(image);
        Review review = reviewService.addReview(addReviewRequest);
        return ResponseEntity.ok(BaseResponse.builder()
                .message(ADD_REVIEW_SUCCESS_MESSAGE)
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
