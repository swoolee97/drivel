package com.ebiz.drivel.domain.profile.service;

import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.member.repository.MemberRepository;
import com.ebiz.drivel.domain.profile.dto.ReportProfileDTO;
import com.ebiz.drivel.domain.profile.entity.Report;
import com.ebiz.drivel.domain.profile.repository.ReportRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ReportServiceTest {

    @InjectMocks
    private ReportService reportService;

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private MemberRepository memberRepository;

    public ReportServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testReportProfile() {
        Long profileId = 1L;
        ReportProfileDTO dto = new ReportProfileDTO();
        dto.setReason("Inappropriate content");
        dto.setDetails("Details about the inappropriate content");

        Member profile = new Member();

        when(memberRepository.findById(profileId)).thenReturn(Optional.of(profile));

        reportService.reportProfile(profileId, dto);

        verify(reportRepository).save(argThat(report -> report.getProfile().equals(profile) &&
                report.getReason().equals("Inappropriate content") &&
                report.getDetails().equals("Details about the inappropriate content")));
    }
}
