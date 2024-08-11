package com.ebiz.drivel.domain.profile.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportProfileDTO {
    private Long profileId;
    private String reason;
    private String details;
}