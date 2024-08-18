package com.ebiz.drivel.domain.report.dto;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportMemberDTO {
    private Long targetId;
    private List<String> descriptions;
}
