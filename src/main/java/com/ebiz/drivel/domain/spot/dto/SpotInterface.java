package com.ebiz.drivel.domain.spot.dto;

public interface SpotInterface {
    Long getId();

    String getTitle();

    String getImagePath();

    double getDistanceFromFirstWaypoint();

    double getDistanceFromLastWaypoint();

}
