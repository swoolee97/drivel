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
    private String carModel;

    public static MeetingConditionDTO from(Meeting meeting) {
        return MeetingConditionDTO.builder()
                .capacity(meeting.getCapacity())
                .meetingPoint(meeting.getMeetingPoint())
                .gender(meeting.getGender().getDisplayName())
                .minCarCareer(meeting.getMinCarCareer())
                .carModel(meeting.getCarModel())
                .build();
    }

}
