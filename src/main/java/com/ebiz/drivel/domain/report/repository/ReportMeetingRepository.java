package com.ebiz.drivel.domain.report.repository;

import com.ebiz.drivel.domain.report.entity.ReportMeeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportMeetingRepository extends JpaRepository<ReportMeeting, Long> {
}
