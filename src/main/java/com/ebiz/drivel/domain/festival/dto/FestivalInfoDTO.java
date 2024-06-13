package com.ebiz.drivel.domain.festival.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FestivalInfoDTO {
    private Long id;

    private String firstAddress;

    private String secondAddress;

    private String title;

    private String startDate;

    private String endDate;

    private String firstImagePath;
}
