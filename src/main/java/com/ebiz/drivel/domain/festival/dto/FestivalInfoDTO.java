package com.ebiz.drivel.domain.festival.dto;

import com.ebiz.drivel.domain.festival.entity.Festival;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FestivalInfoDTO {
    private String id;
    private String title;
    private String imagePath;
    private String startDate;
    private String endDate;

    public static FestivalInfoDTO from(Festival festival) {
        return FestivalInfoDTO.builder()
                .id(festival.getId())
                .title(festival.getTitle())
                .imagePath(festival.getFirstImagePath())
                .startDate(festival.getStartDate())
                .endDate(festival.getEndDate())
                .build();
    }

}
