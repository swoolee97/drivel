package com.ebiz.drivel.domain.push.dto;

import lombok.Data;

@Data
public class NotificationDTO {
    private String title;
    private String body;
    private String sound = "default";
    private String channelId = "500";
}

