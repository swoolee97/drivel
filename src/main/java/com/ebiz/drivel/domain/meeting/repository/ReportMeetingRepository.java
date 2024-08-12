package com.ebiz.drivel.domain.meeting.repository;

import com.ebiz.drivel.domain.meeting.entity.ReportMeeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportMeetingRepository extends JpaRepository<ReportMeeting, Long> {
}
