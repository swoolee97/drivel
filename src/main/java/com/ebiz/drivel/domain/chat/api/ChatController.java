package com.ebiz.drivel.domain.chat.api;

import com.ebiz.drivel.domain.chat.application.ChatService;
import com.ebiz.drivel.domain.chat.dto.ChatMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/meeting/{meetingId}")
    public void sendMessage(@Payload ChatMessageDTO message, @DestinationVariable Long meetingId) {
        chatService.sendMessage(message, meetingId);
    }

    @DeleteMapping("/chat/{id}")
    public void deleteMessage(@PathVariable String id) {
        chatService.deleteMessages(id);
    }

}
