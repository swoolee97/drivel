package com.ebiz.drivel.domain.push.dto;

import lombok.Data;

@Data
public class NotificationDTO {
    private Object title;
    private Object body;
    private String sound = "default";
    private String channelId = "500";
}

