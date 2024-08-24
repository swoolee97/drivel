package com.ebiz.drivel.domain.meeting.api;

import com.ebiz.drivel.domain.chat.dto.ChatMessageDTO;
import com.ebiz.drivel.domain.meeting.application.MeetingBoardService;
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
public class MeetingBoardController {

    private final MeetingBoardService meetingBoardService;

    @GetMapping("/{meetingId}/board/{messageId}")
    public ResponseEntity<List<ChatMessageDTO>> getMeetingMessages(@PathVariable Long meetingId,
                                                                   @PathVariable String messageId) {
        List<ChatMessageDTO> messages = meetingBoardService.getMessages(meetingId, messageId);
        return ResponseEntity.ok(messages);
    }

}
