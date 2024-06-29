package com.ebiz.drivel.domain.meeting.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MeetingParticipantsInfoDTO {
    private Integer capacity;
    private List<MeetingMemberInfoDTO> membersInfo;
}
