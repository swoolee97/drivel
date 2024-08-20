package com.ebiz.drivel.domain.profile.application;

import com.ebiz.drivel.domain.member.repository.MemberRepository;
<<<<<<< HEAD
import com.ebiz.drivel.domain.report.repository.ReportMemberRepository;
import com.ebiz.drivel.domain.report.service.ReportService;
=======
>>>>>>> base/drivel_second
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ReportMemberServiceTest {

    @InjectMocks
    private ReportService reportService;

    @Mock
    private ReportMemberRepository reportMemberRepository;

    @Mock
    private MemberRepository memberRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    public void testReportProfile() {
//        Long profileId = 1L;
//        ReportMemberDTO dto = new ReportMemberDTO(profileId, "부적절한 콘텐츠", "음란물 게시");
//
//        Member profile = Member.builder()
//                .id(profileId)
//                .build();
//
//        when(memberRepository.findById(profileId)).thenReturn(Optional.of(profile));
//
//        reportService.reportProfile(dto);
//
//        verify(reportRepository).save(argThat(report ->
//                report.getMember().equals(profile) &&
//                        report.getReason().equals("부적절한 콘텐츠") &&
//                        report.getDetails().equals("음란물 게시")));
//    }
}
