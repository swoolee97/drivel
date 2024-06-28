package com.ebiz.drivel.domain.meeting.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MeetingInfoDTO {
    private String meetingDescription;
    private MeetingConditionDTO meetingCondition;
    private MeetingMasterInfoDTO meetingMasterInfo;
}
