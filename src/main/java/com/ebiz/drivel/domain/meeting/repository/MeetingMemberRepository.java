package com.ebiz.drivel.domain.meeting.repository;

import com.ebiz.drivel.domain.meeting.entity.Meeting;
import com.ebiz.drivel.domain.meeting.entity.MeetingMember;
import com.ebiz.drivel.domain.meeting.entity.MeetingMemberId;
import com.ebiz.drivel.domain.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingMemberRepository extends JpaRepository<MeetingMember, MeetingMemberId> {

    Optional<MeetingMember> findByMeetingAndMemberAndIsActive(Meeting meeting, Member member, boolean isActive);

}
