package com.ebiz.drivel.domain.report.api;

import static com.ebiz.drivel.domain.profile.api.ProfileController.REPORT_PROFILE_SUCCESS;

<<<<<<< HEAD
import com.ebiz.drivel.domain.report.dto.ReportMeetingDTO;
=======
>>>>>>> base/drivel_second
import com.ebiz.drivel.domain.report.dto.ReportMemberDTO;
import com.ebiz.drivel.domain.report.service.ReportService;
import com.ebiz.drivel.global.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

//    @PostMapping("/meeting")
//    public ResponseEntity<BaseResponse> reportMeeting(@RequestBody ReportMeetingDTO reportMeetingDTO) {
//        reportService.reportMeeting(reportMeetingDTO);
//        BaseResponse response = BaseResponse.builder()
//                .message(REPORT_MEETING_SUCCESS_MESSAGE)
//                .build();
//        return ResponseEntity.ok(response);
//    }

    @PostMapping("/member")
    public ResponseEntity<BaseResponse> reportProfile(@RequestBody ReportMemberDTO reportMemberDTO) {
        reportService.reportMember(reportMemberDTO);
        BaseResponse response = BaseResponse.builder()
                .message(REPORT_PROFILE_SUCCESS)
                .build();
        return ResponseEntity.ok(response);
    }
}
