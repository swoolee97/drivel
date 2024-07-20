package com.ebiz.drivel.domain.sse;

import com.ebiz.drivel.domain.auth.application.UserDetailsServiceImpl;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@Service
@RequiredArgsConstructor
public class SseService {

    private final UserDetailsServiceImpl userDetailsService;
    private final SseRepository sseRepository;

    @Transactional
    public SseEmitter subscribe() {
        Long memberId = userDetailsService.getMemberByContextHolder().getId();

        SseEmitter existingEmitter = sseRepository.findById(memberId);

        if (existingEmitter != null) {
            existingEmitter.complete();
            sseRepository.deleteById(memberId);
        }

        SseEmitter emitter = new SseEmitter(1000 * 60 * 60 * 2L);
        sseRepository.save(memberId, emitter);
        try {
            emitter.send(SseEmitter.event()
                    .name("CONNECT")
                    .data("SSE 연결 성공"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        emitter.onCompletion(() -> {
            sseRepository.deleteById(memberId);
        });

        emitter.onTimeout(() -> {
            emitter.complete();
            sseRepository.deleteById(memberId);
        });

        return emitter;
    }

    public void unsubscribe() {
        Long memberId = userDetailsService.getMemberByContextHolder().getId();
        SseEmitter emitter = sseRepository.findById(memberId);
        emitter.complete();
        if (memberId != null) {
            sseRepository.deleteById(memberId);
        }
    }

    public void sendToClient(Long targetId, String category, Object data) {
        SseEmitter emitter = sseRepository.findById(targetId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .name(category)
                        .data(data));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
