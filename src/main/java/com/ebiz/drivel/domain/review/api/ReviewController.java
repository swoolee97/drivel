package com.ebiz.drivel.domain.review.api;

import com.ebiz.drivel.domain.review.dto.AddReviewDTO;
import com.ebiz.drivel.domain.review.dto.ReviewDTO;
import com.ebiz.drivel.domain.review.entity.Review;
import com.ebiz.drivel.domain.review.service.ReviewService;
import com.ebiz.drivel.global.dto.SuccessResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private static final String ADD_REVIEW_SUCCESS_MESSAGE = "리뷰가 등록되었습니다";

    private final ReviewService reviewService;

    @PostMapping("/add")
    public ResponseEntity<SuccessResponse> addReview(@Valid @RequestBody AddReviewDTO addReviewDTO) {
        Review review = reviewService.addReview(addReviewDTO);
        return ResponseEntity.ok(SuccessResponse.builder()
                .message(ADD_REVIEW_SUCCESS_MESSAGE)
                .build());
    }

    @GetMapping("/my")
    public ResponseEntity<SuccessResponse> findMyReviews() {
        List<ReviewDTO> myReviews = reviewService.findMyReviews();
        return ResponseEntity.ok(SuccessResponse.builder()
                .data(myReviews).build());
    }

}
