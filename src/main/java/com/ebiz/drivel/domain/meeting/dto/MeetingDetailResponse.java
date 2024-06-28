package com.ebiz.drivel.domain.meeting.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MeetingDetailResponse {
    private MeetingInfoDTO meetingInfo;
}
