package com.ebiz.drivel.domain.feedback.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FeedbackDTO {
    private Long targetMemberId;
    private Long meetingId;
    private boolean isPositive;
    private List<Integer> feedbackIds;
}
