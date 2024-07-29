package com.ebiz.drivel.domain.meeting.repository;

import com.ebiz.drivel.domain.meeting.entity.MeetingJoinRequest;
import com.ebiz.drivel.domain.meeting.entity.MeetingJoinRequest.Status;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingJoinRequestRepository extends JpaRepository<MeetingJoinRequest, Long> {
    Optional<MeetingJoinRequest> findAllByMemberIdAndMeetingIdAndStatus(Long memberId, Long meetingId, Status status);
}
