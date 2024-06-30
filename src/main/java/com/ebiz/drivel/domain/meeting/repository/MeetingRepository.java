package com.ebiz.drivel.domain.meeting.repository;

import com.ebiz.drivel.domain.meeting.entity.Gender;
import com.ebiz.drivel.domain.meeting.entity.Meeting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    @Query("SELECT m FROM Meeting m WHERE " +
            "(:age IS NULL OR (m.startAge IS NULL OR (m.startAge <= :age AND m.endAge >= :age))) AND " +
            "(:gender = 'NONE' OR m.gender = :gender OR m.gender = 'NONE') AND " +
            "(:carModel IS NULL OR m.carModel = :carModel) AND " +
            "(m.meetingDate >= CURRENT_DATE) AND " +
            "(m.isActive = TRUE)")
    Page<Meeting> findByCondition(@Param("age") Integer age,
                                  @Param("gender") Gender gender,
                                  @Param("carModel") String carModel,
                                  Pageable pageable);
}
