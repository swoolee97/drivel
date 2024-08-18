package com.ebiz.drivel.domain.meeting.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MeetingNoticeDTO {
    @NotNull
    private Long meetingId;
    @NotNull
    private String content;
}
