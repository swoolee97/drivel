package com.ebiz.drivel.domain.sse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sse")
public class SseController {
    private final SseServiceImpl sseService;

    /*
     * 실시간 알림용 sse 연결하는 api 연결 할 때 더미데이터라도 send를 꼭 해줘야 함. 아니면 timeout 연결시 못받은 알림들을 다 보내줘야함.
     * 연결시에는 유저가 못받은 알림들을 다 보내줌.
     */
    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> connect() {
        SseEmitter emitter = sseService.subscribe();
        return ResponseEntity.ok(emitter);
    }

    @GetMapping("/disconnect")
    public void disconnect() {
        sseService.unsubscribe();
    }
}
