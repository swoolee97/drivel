package com.ebiz.drivel.domain.meeting.repository;

import com.ebiz.drivel.domain.meeting.entity.Meeting;
import com.ebiz.drivel.domain.meeting.entity.MeetingJoinRequest;
import com.ebiz.drivel.domain.meeting.entity.MeetingJoinRequest.Status;
import com.ebiz.drivel.domain.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingJoinRequestRepository extends JpaRepository<MeetingJoinRequest, Long> {
    Optional<MeetingJoinRequest> findByMemberAndMeetingAndStatus(Member member, Meeting meeting, Status status);
}
