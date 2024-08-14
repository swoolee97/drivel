package com.ebiz.drivel.domain.profile.service;

import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.member.repository.MemberRepository;
import com.ebiz.drivel.domain.profile.dto.ReportProfileDTO;
import com.ebiz.drivel.domain.profile.entity.Report;
import com.ebiz.drivel.domain.profile.exception.ProfileException;
import com.ebiz.drivel.domain.profile.repository.ReportRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final MemberRepository memberRepository;

    private Long getCurrentMemberId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Optional<Member> memberOptional = memberRepository.findMemberByEmail(email);

        if(memberOptional.isPresent()) {
            return memberOptional.get().getId();
        } else{
            throw new UsernameNotFoundException("유저 이메일을 찾을 수 없습니다.");
        }
    }

    @Transactional
    public void reportMember(ReportProfileDTO reportProfileDTO) {
        Long currentMemberId = getCurrentMemberId();
        Long reportedMemberId = reportProfileDTO.getReportedId();

        Member reportedMember = memberRepository.findById(reportedMemberId)
                .orElseThrow(() -> ProfileException.userNotFound());

        Member currentMember = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> ProfileException.userNotFound());

        Report report = Report.builder()
                .member(currentMember) // 신고한 사용자
                .reportedMember(reportedMember) // 신고받은 사용자
                .reason(reportProfileDTO.getReason())
                .build();

        reportRepository.save(report);
    }
}
