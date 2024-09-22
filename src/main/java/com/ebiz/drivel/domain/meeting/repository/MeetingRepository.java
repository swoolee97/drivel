package com.ebiz.drivel.domain.meeting.repository;

import com.ebiz.drivel.domain.meeting.entity.Meeting;
import com.ebiz.drivel.domain.meeting.entity.Meeting.MeetingStatus;
import com.ebiz.drivel.domain.member.entity.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    List<Meeting> findByMasterMemberAndStatus(Member member, MeetingStatus status);

    List<Meeting> findMeetingsByMasterMemberAndStatusNot(Member member, MeetingStatus status);
}
