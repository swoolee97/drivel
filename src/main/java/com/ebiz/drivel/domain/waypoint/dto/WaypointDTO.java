package com.ebiz.drivel.domain.waypoint.dto;

import com.ebiz.drivel.domain.waypoint.entity.Waypoint;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WaypointDTO {

    private Long id;

    private String name;

    private int order;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private String description;

    public static WaypointDTO from(Waypoint waypoint) {
        return WaypointDTO.builder()
                .id(waypoint.getId())
                .name(waypoint.getName())
                .order(waypoint.getOrder())
                .latitude(waypoint.getLatitude())
                .longitude(waypoint.getLongitude())
                .description(waypoint.getDescription())
                .build();
    }

}
