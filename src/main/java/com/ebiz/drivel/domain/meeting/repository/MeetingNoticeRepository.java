package com.ebiz.drivel.domain.meeting.repository;

import com.ebiz.drivel.domain.meeting.entity.MeetingNotice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingNoticeRepository extends JpaRepository<MeetingNotice, Long> {
}
