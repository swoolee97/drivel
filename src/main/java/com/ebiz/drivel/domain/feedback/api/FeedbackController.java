package com.ebiz.drivel.domain.feedback.api;

import com.ebiz.drivel.domain.feedback.dto.FeedbackDTO;
import com.ebiz.drivel.domain.feedback.service.FeedbackService;
import com.ebiz.drivel.global.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<BaseResponse> feedbackMeetingMember(@RequestBody FeedbackDTO feedbackDTO) {
        feedbackService.saveFeedbacks(feedbackDTO.isPositive(), feedbackDTO.getMeetingId(),
                feedbackDTO.getTargetMemberId(), feedbackDTO.getFeedbackIds());
        return ResponseEntity.ok(BaseResponse.builder()
                .message("반영 되었습니다")
                .build());
    }

}
