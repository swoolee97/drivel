package com.ebiz.drivel.domain.meeting.dto;

import java.util.Date;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MeetingInfoDTO {
    private Long id;
    private String title;
    private String description;
    private Date date;
    private MeetingConditionDTO condition;
    private MeetingMasterInfoDTO masterInfo;
    private MeetingParticipantsInfoDTO participantsInfo;
}
