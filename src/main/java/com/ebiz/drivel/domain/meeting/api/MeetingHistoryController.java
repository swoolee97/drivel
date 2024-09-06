package com.ebiz.drivel.domain.meeting.api;

import com.ebiz.drivel.domain.meeting.application.MeetingHistoryService;
import com.ebiz.drivel.domain.meeting.dto.MeetingHistoryDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meeting")
public class MeetingHistoryController {

    private final MeetingHistoryService meetingHistoryService;

    @GetMapping("/created")
    public ResponseEntity<List<MeetingHistoryDTO>> getMyCreatedMeetings() {
        List<MeetingHistoryDTO> myMeetings = meetingHistoryService.getCreatedMeetings();
        return ResponseEntity.ok(myMeetings);
    }

    @GetMapping("/participating")
    public ResponseEntity<List<MeetingHistoryDTO>> getMyParticipatingMeetings() {
        List<MeetingHistoryDTO> participatingMeetings = meetingHistoryService.getParticipatingMeetings();
        return ResponseEntity.ok(participatingMeetings);
    }

    @GetMapping("/created/{id}")
    public ResponseEntity<List<MeetingHistoryDTO>> getCreatedMeetings(@PathVariable Long id) {
        List<MeetingHistoryDTO> myMeetings = meetingHistoryService.getCreatedMeetings(id);
        return ResponseEntity.ok(myMeetings);
    }

    @GetMapping("/participating/{id}")
    public ResponseEntity<List<MeetingHistoryDTO>> getParticipatingMeetings(@PathVariable Long id) {
        List<MeetingHistoryDTO> participatingMeetings = meetingHistoryService.getParticipatingMeetings(id);
        return ResponseEntity.ok(participatingMeetings);
    }
}
