package com.ebiz.drivel.domain.sse;

import com.ebiz.drivel.domain.sse.MeetingNotification.AlertCategory;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationDTO {

    private Long id;
    private AlertCategory category;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private boolean isRead;

    public static NotificationDTO from(MeetingNotification meetingNotification) {
        return NotificationDTO.builder()
                .id(meetingNotification.getId())
                .category(meetingNotification.getAlertCategory())
                .title(meetingNotification.getTitle())
                .content(meetingNotification.getContent())
                .createdAt(meetingNotification.getCreatedAt())
                .isRead(meetingNotification.isRead())
                .build();

    }
}
