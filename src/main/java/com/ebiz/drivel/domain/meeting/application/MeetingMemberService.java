package com.ebiz.drivel.domain.meeting.application;

import com.ebiz.drivel.domain.auth.application.UserDetailsServiceImpl;
import com.ebiz.drivel.domain.meeting.entity.Meeting;
import com.ebiz.drivel.domain.meeting.entity.MeetingMember;
import com.ebiz.drivel.domain.meeting.entity.MeetingMemberId;
import com.ebiz.drivel.domain.meeting.repository.MeetingMemberRepository;
import com.ebiz.drivel.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeetingMemberService {

    private final UserDetailsServiceImpl userDetailsService;
    private final MeetingMemberRepository meetingMemberRepository;

    public MeetingMember insertMeetingMember(Meeting meeting) {
        Member member = userDetailsService.getMemberByContextHolder();
        MeetingMember meetingMember = MeetingMember.builder()
                .meetingMemberId(MeetingMemberId.builder()
                        .memberId(member.getId())
                        .meetingId(meeting.getId())
                        .build())
                .build();
        return meetingMemberRepository.save(meetingMember);
    }

}
