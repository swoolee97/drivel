package com.ebiz.drivel.domain.meeting.api;

import com.ebiz.drivel.domain.meeting.application.MeetingService;
import com.ebiz.drivel.domain.meeting.dto.CreateMeetingRequest;
import com.ebiz.drivel.domain.meeting.dto.MeetingDetailResponse;
import com.ebiz.drivel.domain.meeting.dto.MeetingInfoResponse;
import com.ebiz.drivel.domain.meeting.dto.MeetingListRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meeting")
public class MeetingController {

    private final MeetingService meetingService;

    @PostMapping
    public ResponseEntity<MeetingInfoResponse> createMeeting(
            @Valid @RequestBody CreateMeetingRequest createMeetingRequest) {
        MeetingInfoResponse meetingInfoResponse = meetingService.createMeeting(createMeetingRequest);
        return ResponseEntity.ok(meetingInfoResponse);
    }

    @GetMapping
    public ResponseEntity<Page<MeetingInfoResponse>> getMeetingsInfo(MeetingListRequest meetingListRequest,
                                                                     Pageable pageable) {
        Page<MeetingInfoResponse> meetings = meetingService.getMeetings(meetingListRequest, pageable);
        return ResponseEntity.ok(meetings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeetingDetailResponse> getMeetingDetail(@PathVariable Long id) {
        MeetingDetailResponse meetingDetailResponse = meetingService.getMeetingDetail(id);
        return ResponseEntity.ok(meetingDetailResponse);
    }

}
