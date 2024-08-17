package com.ebiz.drivel.domain.report.service;

import com.ebiz.drivel.domain.auth.application.UserDetailsServiceImpl;
import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.member.exception.MemberNotFoundException;
import com.ebiz.drivel.domain.member.repository.MemberRepository;
import com.ebiz.drivel.domain.report.dto.ReportMemberDTO;
import com.ebiz.drivel.domain.report.entity.ReportMember;
import com.ebiz.drivel.domain.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final MemberRepository memberRepository;
    private final UserDetailsServiceImpl userDetailsService;

    public void reportMember(ReportMemberDTO reportMemberDTO) {
        Member member = userDetailsService.getMemberByContextHolder();

        Member targetMember = memberRepository.findById(reportMemberDTO.getTargetMemberId())
                .orElseThrow(() -> new MemberNotFoundException("찾을 수 없는 유저입니다"));

        ReportMember reportMember = ReportMember.builder()
                .member(member)
                .targetMember(targetMember)
                .reason(reportMemberDTO.getDescriptions())
                .build();

        reportRepository.save(reportMember);
    }

//    public void reportMeeting(ReportMeetingDTO reportMeetingDTO) {
//        // 모임 신고 구현
//        // 모임 신고 할 때는 신고자 id도 저장돼야 함.
//    }

}
