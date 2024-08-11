package com.ebiz.drivel.domain.place.repository;

import com.ebiz.drivel.domain.place.dto.PlaceInterface;
import com.ebiz.drivel.domain.place.entity.Place;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    @Query(value =
            "SELECT p.id AS id, p.name AS name, p.image_path AS imagePath, " +
                    "(6371 * ACOS(COS(RADIANS(:firstWaypointLat)) * COS(RADIANS(p.latitude)) * COS(RADIANS(p.longitude) - RADIANS(:firstWaypointLon)) + SIN(RADIANS(:firstWaypointLat)) * SIN(RADIANS(p.latitude)))) AS distanceFromFirstWaypoint, "
                    +
                    "(6371 * ACOS(COS(RADIANS(:lastWaypointLat)) * COS(RADIANS(p.latitude)) * COS(RADIANS(p.longitude) - RADIANS(:lastWaypointLon)) + SIN(RADIANS(:lastWaypointLat)) * SIN(RADIANS(p.latitude)))) AS distanceFromLastWaypoint "
                    +
                    "FROM place p " +
                    "WHERE (6371 * ACOS(COS(RADIANS(:firstWaypointLat)) * COS(RADIANS(p.latitude)) * COS(RADIANS(p.longitude) - RADIANS(:firstWaypointLon)) + SIN(RADIANS(:firstWaypointLat)) * SIN(RADIANS(p.latitude)))) <= 10 "
                    +
                    "OR (6371 * ACOS(COS(RADIANS(:lastWaypointLat)) * COS(RADIANS(p.latitude)) * COS(RADIANS(p.longitude) - RADIANS(:lastWaypointLon)) + SIN(RADIANS(:lastWaypointLat)) * SIN(RADIANS(p.latitude)))) <= 10 "
                    +
                    "ORDER BY distanceFromFirstWaypoint, distanceFromLastWaypoint", nativeQuery = true)
    List<PlaceInterface> findPlacesNearby(@Param("firstWaypointLat") double firstWaypointLat,
                                          @Param("firstWaypointLon") double firstWaypointLon,
                                          @Param("lastWaypointLat") double lastWaypointLat,
                                          @Param("lastWaypointLon") double lastWaypointLon);
}
