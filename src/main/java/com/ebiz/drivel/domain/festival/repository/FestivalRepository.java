package com.ebiz.drivel.domain.festival.repository;

import com.ebiz.drivel.domain.festival.dto.FestivalInfoInterface;
import com.ebiz.drivel.domain.festival.entity.Festival;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FestivalRepository extends JpaRepository<Festival, String> {

    @Query(value = "SELECT f.id AS id, f.title AS title, f.first_image_path AS firstImagePath, " +
            "(6371 * ACOS(" +
            "  COS(RADIANS(:givenLat)) * COS(RADIANS(f.latitude)) * COS(RADIANS(f.longitude) - RADIANS(:givenLon)) " +
            "+ SIN(RADIANS(:givenLat)) * SIN(RADIANS(f.latitude)) " +
            ")) AS distance " +
            "FROM festival f " +
            "HAVING distance <= 30 "
            + "ORDER BY 4", nativeQuery = true)
    List<FestivalInfoInterface> findIdTitleFirstImagePathByDistance(@Param("givenLat") double givenLat,
                                                                    @Param("givenLon") double givenLon);
}
