package com.ebiz.drivel.domain.meeting.dto;

import com.ebiz.drivel.domain.meeting.entity.Meeting;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MeetingHistoryDTO {
    private Long meetingId;
    private String imagePath;
    private String title;
    private Date date;

    public static MeetingHistoryDTO from(Meeting meeting) {
        return MeetingHistoryDTO.builder()
                .meetingId(meeting.getId())
                .title(meeting.getTitle())
                .imagePath(meeting.getCourse().getImagePath())
                .date(meeting.getMeetingDate())
                .build();
    }
}
