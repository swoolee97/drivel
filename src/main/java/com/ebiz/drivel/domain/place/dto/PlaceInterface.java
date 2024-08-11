package com.ebiz.drivel.domain.place.dto;

public interface PlaceInterface {
    Long getId();

    String getName();

    String getImagePath();

    double getDistanceFromFirstWaypoint();

    double getDistanceFromLastWaypoint();
}
