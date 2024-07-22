package com.ebiz.drivel.domain.sse;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SseService {
    SseEmitter subscribe();

    void sendToClient(Long targetId, String category, Object data);
}
