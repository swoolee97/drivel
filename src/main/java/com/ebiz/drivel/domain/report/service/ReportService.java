package com.ebiz.drivel.domain.report.service;

import com.ebiz.drivel.domain.auth.application.UserDetailsServiceImpl;
import com.ebiz.drivel.domain.meeting.entity.Meeting;
import com.ebiz.drivel.domain.meeting.exception.MeetingNotFoundException;
import com.ebiz.drivel.domain.meeting.repository.MeetingRepository;
import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.member.exception.MemberNotFoundException;
import com.ebiz.drivel.domain.member.repository.MemberRepository;
import com.ebiz.drivel.domain.report.dto.ReportMeetingDTO;
import com.ebiz.drivel.domain.report.dto.ReportMemberDTO;
import com.ebiz.drivel.domain.report.entity.ReportMeeting;
import com.ebiz.drivel.domain.report.entity.ReportMember;
import com.ebiz.drivel.domain.report.repository.ReportMeetingRepository;
import com.ebiz.drivel.domain.report.repository.ReportMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReportService {

    private final ReportMemberRepository reportMemberRepository;
    private final ReportMeetingRepository reportMeetingRepository;
    private final MemberRepository memberRepository;
    private final MeetingRepository meetingRepository;
    private final UserDetailsServiceImpl userDetailsService;

    public void reportMember(ReportMemberDTO reportMemberDTO) {
        Member member = userDetailsService.getMemberByContextHolder();

        Member targetMember = memberRepository.findById(reportMemberDTO.getTargetId())
                .orElseThrow(() -> new MemberNotFoundException("찾을 수 없는 유저입니다"));

        ReportMember reportMember = ReportMember.builder()
                .member(member)
                .targetMember(targetMember)
                .descriptions(reportMemberDTO.getDescriptions())
                .build();

        reportMemberRepository.save(reportMember);
    }


    public void reportMeeting(ReportMeetingDTO reportMeetingDTO) {
        // 모임 신고 구현
        // 모임 신고 할 때는 신고자 id도 저장돼야 함.
        Member member = userDetailsService.getMemberByContextHolder();
        Meeting meeting = meetingRepository.findById(reportMeetingDTO.getTargetId())
                .orElseThrow(() -> new MeetingNotFoundException("찾을 수 없는 모임입니다"));
        ReportMeeting reportMeeting = ReportMeeting.builder()
                .member(member)
                .meeting(meeting)
                .summary(reportMeetingDTO.getSummary())
                .description(reportMeetingDTO.getDescription())
                .build();
        reportMeetingRepository.save(reportMeeting);
    }

}
