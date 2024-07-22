package com.ebiz.drivel.domain.sse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<MeetingNotification, Long> {
}