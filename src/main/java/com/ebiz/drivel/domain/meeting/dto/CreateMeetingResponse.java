package com.ebiz.drivel.domain.meeting.dto;

import com.ebiz.drivel.global.dto.BaseResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class CreateMeetingResponse extends BaseResponse {
    private Long meetingId;
    private Long courseId;
}
