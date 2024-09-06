package com.ebiz.drivel.domain.meeting.dto;

import com.ebiz.drivel.domain.course.entity.Course;
import com.ebiz.drivel.domain.meeting.entity.Meeting;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MeetingHistoryDTO {
    private Long meetingId;
    private Long courseId;
    private String imagePath;
    private String title;
    private Date date;

    public static MeetingHistoryDTO from(Meeting meeting) {
        Course course = meeting.getCourse();

        return MeetingHistoryDTO.builder()
                .meetingId(meeting.getId())
                .courseId(course.getId())
                .title(meeting.getTitle())
                .imagePath(course.getImagePath())
                .date(meeting.getMeetingDate())
                .build();
    }
}
