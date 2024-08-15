package com.ebiz.drivel.domain.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportMeetingDTO {
    private Long meetingId;
    private String reason;
    private String details;
}
