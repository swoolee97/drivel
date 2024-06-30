package com.ebiz.drivel.domain.meeting.dto;

import com.ebiz.drivel.domain.meeting.entity.Meeting;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MeetingConditionDTO {
    private String meetingPoint;
    private Integer capacity;
    private String gender;
    private Integer minCarCareer;
    private Integer startAge;
    private Integer endAge;
    private String carModel;

    public static MeetingConditionDTO from(Meeting meeting) {
        return MeetingConditionDTO.builder()
                .meetingPoint(meeting.getMeetingPoint())
                .capacity(meeting.getCapacity())
                .gender(meeting.getGender().getDisplayName())
                .minCarCareer(meeting.getMinCarCareer())
                .startAge(meeting.getStartAge())
                .endAge(meeting.getEndAge())
                .carModel(meeting.getCarModel())
                .build();
    }

}
