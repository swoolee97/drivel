package com.ebiz.drivel.domain.message;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    @MessageMapping("/chat/{id}")
    public void abc() {

    }

}
