package com.ebiz.drivel.domain.festival.entity;

import com.ebiz.drivel.domain.festival.dto.FestivalCommonApiResponse;
import com.ebiz.drivel.domain.festival.dto.FestivalDetailApiResponse;
import com.ebiz.drivel.domain.festival.dto.FestivalInfoApiResponse.Item;
import com.ebiz.drivel.domain.festival.dto.FestivalIntroApiResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@Table(name = "festival")
public class Festival {

    @Id
    private String id;

    @Column(name = "first_address")
    private String firstAddress;

    @Column(name = "second_address")
    private String secondAddress;

    @Column(name = "title")
    private String title;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "first_image_path")
    private String firstImagePath;

    @Column(name = "second_image_path")
    private String secondImagePath;

    @Column(name = "latitude", nullable = false, precision = 12, scale = 10)
    private BigDecimal latitude;

    @Column(name = "longitude", nullable = false, precision = 13, scale = 10)
    private BigDecimal longitude;

    @Column(name = "description")
    private String description;

    @Column(name = "sponsor")
    private String sponsor;

    @Column(name = "sponsor_tel")
    private String sponsorTel;

    @Column(name = "play_time")
    private String playTime;

    @Column(name = "event_place")
    private String eventPlace;

    @Column(name = "event_homepage")
    private String eventHomepage;

    @Column(name = "age_limit")
    private String ageLimit;

    @Column(name = "booking_place")
    private String bookingPlace;

    @Column(name = "place_info")
    private String placeInfo;

    @Column(name = "sub_event")
    private String subEvent;

    @Column(name = "program")
    private String program;

    @Column(name = "use_time_festival")
    private String useTimeFestival;

    @Column(name = "spend_time_festival")
    private String spendTimeFestival;

    @Column(name = "festival_grade")
    private String festivalGrade;

    @Column(name = "serial_num")
    private String serialNum;

    @Column(name = "info_name")
    private String infoName;

    @Column(name = "info_text")
    private String infoText;

    public static Festival from(Item item, String description) {
        return Festival.builder()
                .id(item.getContentid())
                .firstAddress(item.getAddr1())
                .secondAddress(item.getAddr2())
                .title(item.getTitle())
                .startDate(item.getEventstartdate())
                .endDate(item.getEventenddate())
                .firstImagePath(item.getFirstimage())
                .secondImagePath(item.getFirstimage2())
                .latitude(convertCoordinate(item.getMapy()))
                .longitude(convertCoordinate(item.getMapx()))
                .description(description)
                .build();
    }

    public static Festival from(FestivalCommonApiResponse commonApiResponse, FestivalIntroApiResponse introApiResponse,
                                FestivalDetailApiResponse detailApiResponse) {
        FestivalCommonApiResponse.Item commonApiItem = commonApiResponse.getResponse().getBody().getItems().getItem()
                .get(0);
        FestivalIntroApiResponse.Item introApiItem = introApiResponse.getResponse().getBody().getItems().getItem()
                .get(0);
        FestivalDetailApiResponse.Item detailApiItem = detailApiResponse.getResponse().getBody().getItems().getItem()
                .get(0);

        return Festival.builder()
                .id(commonApiItem.getContentid())
                .firstAddress(commonApiItem.getAddr1())
                .secondAddress(commonApiItem.getAddr2())
                .title(commonApiItem.getTitle())
                .startDate(introApiItem.getEventstartdate())
                .endDate(introApiItem.getEventenddate())
                .firstImagePath(commonApiItem.getFirstimage())
                .secondImagePath(commonApiItem.getFirstimage2())
                .latitude(convertCoordinate(commonApiItem.getMapy()))
                .longitude(convertCoordinate(commonApiItem.getMapx()))
                .description(commonApiItem.getOverview())
                .sponsor(introApiItem.getSponsor1())
                .sponsorTel(introApiItem.getSponsor1tel())
                .playTime(introApiItem.getPlaytime())
                .eventPlace(introApiItem.getEventplace())
                .eventHomepage(introApiItem.getEventhomepage())
                .ageLimit(introApiItem.getAgelimit())
                .bookingPlace(introApiItem.getBookingplace())
                .placeInfo(introApiItem.getPlaceinfo())
                .subEvent(introApiItem.getSubevent())
                .program(introApiItem.getProgram())
                .useTimeFestival(introApiItem.getUsetimefestival())
                .spendTimeFestival(introApiItem.getSpendtimefestival())
                .festivalGrade(introApiItem.getFestivalgrade())
                .serialNum(detailApiItem.getSerialnum())
                .infoName(detailApiItem.getInfoname())
                .infoText(detailApiItem.getInfotext())
                .build();
    }

    private static BigDecimal convertCoordinate(String decimal) {
        return BigDecimal.valueOf(Double.valueOf(decimal));
    }

}
