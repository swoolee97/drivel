package com.ebiz.drivel.domain.meeting.api;

import com.ebiz.drivel.domain.meeting.application.MeetingQueryHelper.OrderBy;
import com.ebiz.drivel.domain.meeting.application.MeetingService;
import com.ebiz.drivel.domain.meeting.dto.CreateMeetingRequest;
import com.ebiz.drivel.domain.meeting.dto.CreateMeetingResponse;
import com.ebiz.drivel.domain.meeting.dto.JoinRequestDTO;
import com.ebiz.drivel.domain.meeting.dto.JoinRequestDecisionDTO;
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

    @GetMapping("/upcoming")
    public ResponseEntity<List<UpcomingMeetingResponse>> getUpcomingMeetings() {
        List<UpcomingMeetingResponse> upcomingMeetings = meetingService.getUpcomingMeetings();
        return ResponseEntity.ok(upcomingMeetings);
    }

    @PostMapping("/join")
    public ResponseEntity<BaseResponse> joinMeeting(@RequestBody JoinRequestDTO joinRequestDTO) {
        meetingService.requestJoinMeeting(joinRequestDTO.getId());
        return ResponseEntity.ok(BaseResponse.builder()
                .message("모임 가입 신청이 완료되었습니다")
                .build());
    }

    @PostMapping("/accept")
    public void decideJoinMeeting(@RequestBody JoinRequestDecisionDTO joinRequestDecisionDTO) {
        meetingService.acceptJoinMeeting(joinRequestDecisionDTO);
    }

}
