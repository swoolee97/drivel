package com.ebiz.drivel.domain.profile.service;

import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.member.repository.MemberRepository;
import com.ebiz.drivel.domain.profile.dto.ReportProfileDTO;
import com.ebiz.drivel.domain.profile.entity.Report;
import com.ebiz.drivel.domain.profile.repository.ReportRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final MemberRepository memberRepository;

    public ReportService(ReportRepository reportRepository, MemberRepository memberRepository) {
        this.reportRepository = reportRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void reportProfile(ReportProfileDTO reportProfileDTO) {
        Long profileId = reportProfileDTO.getProfileId();

        Member profile = memberRepository.findById(profileId)
                .orElseThrow(() -> new IllegalArgumentException("프로필을 찾을 수 없습니다."));

        Report report = Report.builder()
                .profile(profile)
                .reason(reportProfileDTO.getReason())
                .details(reportProfileDTO.getDetails())
                .build();

        reportRepository.save(report);
    }
}
