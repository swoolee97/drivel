package com.ebiz.drivel.domain.meeting.dto;

import java.util.Date;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpcomingMeetingResponse {
    private Long id;
    private String title;
    private Date meetingDate;
}
