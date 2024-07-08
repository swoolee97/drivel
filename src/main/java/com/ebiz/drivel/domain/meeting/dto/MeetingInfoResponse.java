package com.ebiz.drivel.domain.meeting.dto;

import com.ebiz.drivel.domain.meeting.entity.Meeting;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MeetingInfoResponse {
    private Long meetingId;
    private Long courseId;
    private String meetingTitle;
    private String courseTitle;
    private String meetingPoint;
    private Integer capacity;
    private Long participantsCount;
    private Integer startAge;
    private Integer endAge;
    private String gender;
    private String carModel;
    private String imagePath;
    private Integer minCarCareer;

    public static MeetingInfoResponse from(Meeting meeting) {
        return MeetingInfoResponse.builder()
                .meetingId(meeting.getId())
                .courseId(meeting.getCourse().getId())
                .meetingTitle(meeting.getTitle())
                .courseTitle(meeting.getCourse().getTitle())
                .meetingPoint(meeting.getMeetingPoint())
                .capacity(meeting.getCapacity())
                .participantsCount(meeting.countParticipants())
                .startAge(meeting.getStartAge())
                .endAge(meeting.getEndAge())
                .gender(meeting.getGender().getDisplayName())
                .carModel(meeting.getCarModel())
                .imagePath(meeting.getCourse().getImagePath())
                .minCarCareer(meeting.getMinCarCareer())
                .build();
    }
}
