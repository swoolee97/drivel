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
    private String sponsor;
    private String sponsorTel;
    private String playTime;
    private String eventPlace;
    private String eventHomepage;
    private String ageLimit;
    private String bookingPlace;
    private String placeInfo;
    private String subEvent;
    private String program;
    private String useTimeFestival;
    private String spendTimeFestival;
    private String festivalGrade;
    private String infoName;
    private String infoText;

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
                .sponsor(festival.getSponsor())
                .sponsorTel(festival.getSponsorTel())
                .playTime(festival.getPlayTime())
                .eventPlace(festival.getEventPlace())
                .eventHomepage(festival.getEventHomepage())
                .ageLimit(festival.getAgeLimit())
                .bookingPlace(festival.getBookingPlace())
                .placeInfo(festival.getPlaceInfo())
                .subEvent(festival.getSubEvent())
                .program(festival.getProgram())
                .useTimeFestival(festival.getUseTimeFestival())
                .spendTimeFestival(festival.getSpendTimeFestival())
                .festivalGrade(festival.getFestivalGrade())
                .infoName(festival.getInfoName())
                .infoText(festival.getInfoText())
                .build();
    }

}
