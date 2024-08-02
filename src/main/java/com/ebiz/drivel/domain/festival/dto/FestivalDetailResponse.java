package com.ebiz.drivel.domain.festival.dto;

import com.ebiz.drivel.domain.festival.entity.Festival;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FestivalDetailResponse {
    private String id;
    private String firstAddress;
    private String secondAddress;
    private String title;
    private String startDate;
    private String endDate;
    private String imagePath;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String description;

    public static FestivalDetailResponse from(Festival festival) {
        return FestivalDetailResponse.builder()
                .id(festival.getId())
                .firstAddress(festival.getFirstAddress())
                .secondAddress(festival.getSecondAddress())
                .title(festival.getTitle())
                .startDate(festival.getStartDate())
                .endDate(festival.getEndDate())
                .imagePath(festival.getFirstImagePath())
                .latitude(festival.getLatitude())
                .longitude(festival.getLongitude())
                .description(festival.getDescription())
                .build();
    }

}
