package com.ebiz.drivel.domain.meeting.dto;

import com.ebiz.drivel.domain.course.entity.Course;
import com.ebiz.drivel.domain.meeting.entity.Meeting;
import com.ebiz.drivel.domain.meeting.entity.MeetingJoinRequest;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MeetingJoinRequestDTO {
    private Long meetingId;
    private String imagePath;
    private String meetingTitle;
    private String courseTitle;
    private List<MeetingMemberInfoDTO> requestedMembers;

    public static MeetingJoinRequestDTO from(Meeting meeting, List<MeetingJoinRequest> requests) {
        Course course = meeting.getCourse();
        return MeetingJoinRequestDTO.builder()
                .meetingId(meeting.getId())
                .imagePath(course.getImagePath())
                .meetingTitle(meeting.getTitle())
                .courseTitle(course.getTitle())
                .requestedMembers(JoinRequestedMemberInfoDTO.from(requests))
                .build();
    }
}
