package com.ebiz.drivel.domain.profile.application;

import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.member.repository.MemberRepository;
import com.ebiz.drivel.domain.profile.dto.ReportProfileDTO;
import com.ebiz.drivel.domain.profile.entity.Report;
import com.ebiz.drivel.domain.profile.repository.ReportRepository;
import com.ebiz.drivel.domain.profile.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReportServiceTest {

    @InjectMocks
    private ReportService reportService;

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private MemberRepository memberRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testReportProfile() {
        Long profileId = 1L;
        ReportProfileDTO dto = new ReportProfileDTO(profileId, "부적절한 콘텐츠", "음란물 게시");

        Member profile = Member.builder()
                .id(profileId)
                .build();

        when(memberRepository.findById(profileId)).thenReturn(Optional.of(profile));

        reportService.reportProfile(dto);

        verify(reportRepository).save(argThat(report ->
                report.getProfile().equals(profile) &&
                        report.getReason().equals("부적절한 콘텐츠") &&
                        report.getDetails().equals("음란물 게시")));
    }
}
