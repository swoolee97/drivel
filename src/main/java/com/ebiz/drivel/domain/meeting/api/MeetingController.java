package com.ebiz.drivel.domain.meeting.api;

import com.ebiz.drivel.domain.meeting.application.MeetingQueryHelper.OrderBy;
import com.ebiz.drivel.domain.meeting.application.MeetingService;
import com.ebiz.drivel.domain.meeting.dto.CreateMeetingRequest;
import com.ebiz.drivel.domain.meeting.dto.CreateMeetingResponse;
import com.ebiz.drivel.domain.meeting.dto.MeetingDetailResponse;
import com.ebiz.drivel.domain.meeting.dto.MeetingInfoResponse;
import com.ebiz.drivel.domain.meeting.dto.UpcomingMeetingResponse;
import com.ebiz.drivel.global.dto.BaseResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meeting")
public class MeetingController {

    public static final String REPORT_MEETING_SUCCESS_MESSAGE = "모임이 신고되었습니다.";

    private final MeetingService meetingService;

    @PostMapping
    public ResponseEntity<CreateMeetingResponse> createMeeting(
            @Valid @RequestBody CreateMeetingRequest createMeetingRequest) {
        CreateMeetingResponse createMeetingResponse = meetingService.createMeeting(createMeetingRequest);
        return ResponseEntity.ok(createMeetingResponse);
    }

    @GetMapping
    public ResponseEntity<Page<MeetingInfoResponse>> getMeetingsInfo(@RequestParam(required = false) Long styleId,
                                                                     @RequestParam(required = false) Long themeId,
                                                                     @RequestParam(required = false) Long togetherId,
                                                                     @RequestParam(required = false) Integer age,
                                                                     @RequestParam(required = false) Integer carCareer,
                                                                     @RequestParam(required = false) String carModel,
                                                                     @RequestParam(required = false) Integer genderId,
                                                                     @RequestParam(required = false) OrderBy orderBy,
                                                                     Pageable pageable) {
        Page<MeetingInfoResponse> meetings = meetingService.getFilteredMeetings(styleId, themeId, togetherId, age,
                carCareer, carModel,
                genderId, orderBy, pageable);
        return ResponseEntity.ok(meetings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeetingDetailResponse> getMeetingDetail(@PathVariable Long id) {
        MeetingDetailResponse meetingDetailResponse = meetingService.getMeetingDetail(id);
        return ResponseEntity.ok(meetingDetailResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deleteMeeting(@PathVariable Long id) {
        meetingService.deleteMeeting(id);
        return ResponseEntity.ok(BaseResponse.builder()
                .message("모임이 삭제되었습니다")
                .build());
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<UpcomingMeetingResponse>> getUpcomingMeetings() {
        List<UpcomingMeetingResponse> upcomingMeetings = meetingService.getUpcomingMeetings();
        return ResponseEntity.ok(upcomingMeetings);
    }

    @DeleteMapping("/leave/{id}")
    public ResponseEntity<BaseResponse> leaveMeeting(@PathVariable Long id) {
        meetingService.leaveMeeting(id);
        return ResponseEntity.ok(BaseResponse.builder()
                .message("모임에서 나왔어요")
                .build());
    }

    @PostMapping("/{id}/end")
    public ResponseEntity<BaseResponse> endMeeting(@PathVariable Long id) {
        meetingService.endMeeting(id);
        return ResponseEntity.ok(BaseResponse.builder()
                .message("모임이 종료되었어요")
                .build());
    }

}
