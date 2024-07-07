package com.ebiz.drivel.domain.meeting.dto;

import java.util.Date;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpcomingMeetingResponse {
    private Long meetingId;
    private String title;
    private Date meetingDate;
    private Long courseId;
}
