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
            "SELECT p.id AS id, p.name AS name, p.image_path AS imagePath, "
                    +
                    "(6371 * ACOS(" +
                    "  COS(RADIANS(:givenLat)) * COS(RADIANS(p.latitude)) * COS(RADIANS(p.longitude) - RADIANS(:givenLon)) "
                    +
                    "+ SIN(RADIANS(:givenLat)) * SIN(RADIANS(p.latitude)) " +
                    ")) AS distance " +
                    "FROM place p " +
                    "HAVING distance <= 10 "
                    + "ORDER BY 4", nativeQuery = true)
    List<PlaceInterface> findPlacesNearby(@Param("givenLat") double givenLat, @Param("givenLon") double givenLon);
}
