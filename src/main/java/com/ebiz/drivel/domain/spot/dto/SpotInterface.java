package com.ebiz.drivel.domain.spot.dto;

import java.math.BigDecimal;

public interface SpotInterface {
    Long getId();

    String getTitle();

    String getImagePath();

    BigDecimal getLongitude();

    BigDecimal getLatitude();

    double getDistanceFromFirstWaypoint();

    double getDistanceFromLastWaypoint();

}
