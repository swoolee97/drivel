package com.ebiz.drivel.domain.sse;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public class NotificationDTO {

    private Long id;
    private Alert category;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private boolean isRead;

    public static NotificationDTO from(Notification notification) {
        return NotificationDTO.builder()
                .id(notification.getId())
                .category(notification.getCategory())
                .title(notification.getTitle())
                .content(notification.getContent())
                .createdAt(notification.getCreatedAt())
                .isRead(notification.isRead())
                .build();

    }
}
