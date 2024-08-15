package com.ebiz.drivel.domain.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/meeting/{meetingId}")
    public void sendMessage(@Payload ChatMessageDTO message, @DestinationVariable Long meetingId) {
        chatService.sendMessage(message, meetingId);
    }

}
