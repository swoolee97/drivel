package com.ebiz.drivel.domain.meeting.application;

import com.ebiz.drivel.domain.meeting.entity.Meeting;
import com.ebiz.drivel.domain.meeting.entity.MeetingMember;
import com.ebiz.drivel.domain.meeting.entity.MeetingMemberId;
import com.ebiz.drivel.domain.meeting.repository.MeetingMemberRepository;
import com.ebiz.drivel.domain.member.entity.Member;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeetingMemberService {

    private final MeetingMemberRepository meetingMemberRepository;

    public MeetingMember insertMeetingMember(Meeting meeting, Member member) {
        MeetingMemberId meetingMemberId = MeetingMemberId.builder()
                .meetingId(meeting.getId())
                .memberId(member.getId()).build();
        MeetingMember meetingMember = MeetingMember.builder()
                .meetingMemberId(meetingMemberId)
                .meeting(meeting)
                .isActive(true)
                .member(member).build();
        return meetingMemberRepository.save(meetingMember);
    }

    public Optional<MeetingMember> findMeetingMember(Meeting meeting, Member member) {
        return meetingMemberRepository.findByMeetingAndMemberAndIsActive(meeting, member, true);
    }

}
