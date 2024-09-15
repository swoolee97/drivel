package com.ebiz.drivel.domain.onboarding;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MeetingCreateCheckDTO {
    private boolean enableToCreateMeeting;
}
