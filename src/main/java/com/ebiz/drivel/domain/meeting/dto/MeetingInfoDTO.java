package com.ebiz.drivel.domain.meeting.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MeetingInfoDTO {
    private Long id;
    private String description;
    private MeetingConditionDTO condition;
    private MeetingMasterInfoDTO masterInfo;
    private MeetingParticipantsInfoDTO participantsInfo;
}
