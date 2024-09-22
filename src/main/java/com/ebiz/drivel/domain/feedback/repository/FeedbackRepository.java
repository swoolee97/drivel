package com.ebiz.drivel.domain.feedback.repository;

import com.ebiz.drivel.domain.feedback.entity.Feedback;
import com.ebiz.drivel.domain.meeting.entity.Meeting;
import com.ebiz.drivel.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    boolean existsByMemberAndTargetMemberAndMeeting(Member member, Member targetMember, Meeting meeting);
}
