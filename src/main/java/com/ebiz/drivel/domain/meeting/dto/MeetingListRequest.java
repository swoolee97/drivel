package com.ebiz.drivel.domain.meeting.dto;

import lombok.Data;

@Data
public class MeetingListRequest {
    private Integer age;
    private Integer gender;
    private String carModel;
    private Long minCarCareer;
    private Long styleId;
}
