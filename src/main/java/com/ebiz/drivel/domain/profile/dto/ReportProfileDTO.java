package com.ebiz.drivel.domain.profile.dto;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportProfileDTO {
    private Long reportedId;
    private List<String> reason;
}