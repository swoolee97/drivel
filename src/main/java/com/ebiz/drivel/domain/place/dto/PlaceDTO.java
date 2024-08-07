package com.ebiz.drivel.domain.place.dto;

import com.ebiz.drivel.domain.place.entity.Place;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PlaceDTO {
    private Long id;
    private String name;
    private String imagePath;
    private String category;
    private String address;
    private String phoneNumber;
    private BigDecimal longitude;
    private BigDecimal latitude;

    public static PlaceDTO from(Place place) {
        return PlaceDTO.builder()
                .id(place.getId())
                .name(place.getName())
                .imagePath(place.getImagePath())
                .category(place.getCategory())
                .address(place.getAddress())
                .phoneNumber(place.getPhoneNumber())
                .longitude(place.getLongitude())
                .latitude(place.getLatitude())
                .build();
    }

}
