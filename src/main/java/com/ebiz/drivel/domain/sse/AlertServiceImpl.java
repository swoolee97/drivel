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
public class AlertServiceImpl implements AlertService {

    private final UserDetailsServiceImpl userDetailsService;
    private final SseRepository sseRepository;
    private final AlertRepository alertRepository;

    @Override
    @Transactional
    public SseEmitter subscribe() {
        Long memberId = userDetailsService.getMemberByContextHolder().getId();

        SseEmitter existingEmitter = sseRepository.findById(memberId);

        if (existingEmitter != null) {
            existingEmitter.complete();
            sseRepository.deleteById(memberId);
        }

        SseEmitter emitter = new SseEmitter(1000 * 60L);
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

    @Override
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

    @Transactional
    @Override
    public void read(Long id) {
        Alert alert = alertRepository.findById(id).orElseThrow(() -> new AlertNotFoundException("찾을 수 없는 알람입니다"));
        alert.read();
    }

}
