package com.ebiz.drivel.domain.profile.service;

import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.member.repository.MemberRepository;
import com.ebiz.drivel.domain.profile.dto.ReportProfileDTO;
import com.ebiz.drivel.domain.profile.entity.Report;
import com.ebiz.drivel.domain.profile.exception.ProfileException;
import com.ebiz.drivel.domain.profile.repository.ReportRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void reportProfile(ReportProfileDTO reportProfileDTO) {
        Long profileId = reportProfileDTO.getProfileId();

        Member profile = memberRepository.findById(profileId)
                .orElseThrow(ProfileException::userNotFound);

        Report report = Report.builder()
                .profile(profile)
                .reason(reportProfileDTO.getReason())
                .details(reportProfileDTO.getDetails())
                .build();

        reportRepository.save(report);
    }
}
