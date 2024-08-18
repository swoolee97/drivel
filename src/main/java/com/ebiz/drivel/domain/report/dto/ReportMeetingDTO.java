package com.ebiz.drivel.domain.report.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportMeetingDTO {
    private Long targetId;
    private String summary;
    private String description;
}
