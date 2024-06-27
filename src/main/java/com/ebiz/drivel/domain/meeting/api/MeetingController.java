package com.ebiz.drivel.domain.meeting.api;

import com.ebiz.drivel.domain.meeting.application.MeetingService;
import com.ebiz.drivel.domain.meeting.dto.CreateMeetingRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public void createMeeting(@Valid @RequestBody CreateMeetingRequest createMeetingRequest) {
        meetingService.createMeeting(createMeetingRequest);
    }

}
