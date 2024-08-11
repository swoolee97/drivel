package com.ebiz.drivel.domain.spot.repository;

import com.ebiz.drivel.domain.spot.dto.SpotInterface;
import com.ebiz.drivel.domain.spot.entity.Spot;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotRepository extends JpaRepository<Spot, Integer> {

    @Query(value =
            "SELECT s.id AS id, s.title AS title, s.first_image AS imagePath, " +
                    "(6371 * ACOS(COS(RADIANS(:firstWaypointLat)) * COS(RADIANS(s.latitude)) * COS(RADIANS(s.longitude) - RADIANS(:firstWaypointLon)) + SIN(RADIANS(:firstWaypointLat)) * SIN(RADIANS(s.latitude)))) AS distanceFromFirstWaypoint, "
                    +
                    "(6371 * ACOS(COS(RADIANS(:lastWaypointLat)) * COS(RADIANS(s.latitude)) * COS(RADIANS(s.longitude) - RADIANS(:lastWaypointLon)) + SIN(RADIANS(:lastWaypointLat)) * SIN(RADIANS(s.latitude)))) AS distanceFromLastWaypoint "
                    +
                    "FROM spot s " +
                    "WHERE (6371 * ACOS(COS(RADIANS(:firstWaypointLat)) * COS(RADIANS(s.latitude)) * COS(RADIANS(s.longitude) - RADIANS(:firstWaypointLon)) + SIN(RADIANS(:firstWaypointLat)) * SIN(RADIANS(s.latitude)))) <= 10 "
                    +
                    "OR (6371 * ACOS(COS(RADIANS(:lastWaypointLat)) * COS(RADIANS(s.latitude)) * COS(RADIANS(s.longitude) - RADIANS(:lastWaypointLon)) + SIN(RADIANS(:lastWaypointLat)) * SIN(RADIANS(s.latitude)))) <= 10 "
                    +
                    "ORDER BY distanceFromFirstWaypoint, distanceFromLastWaypoint", nativeQuery = true)
    List<SpotInterface> findSpotsNearby(
            @Param("firstWaypointLat") double firstWaypointLat,
            @Param("firstWaypointLon") double firstWaypointLon,
            @Param("lastWaypointLat") double lastWaypointLat,
            @Param("lastWaypointLon") double lastWaypointLon
    );

}
