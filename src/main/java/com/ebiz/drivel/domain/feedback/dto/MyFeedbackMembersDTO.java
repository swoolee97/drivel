package com.ebiz.drivel.domain.feedback.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MyFeedbackMembersDTO {
    List<Long> feedbackMemberIds;
}
