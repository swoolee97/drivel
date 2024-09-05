package com.ebiz.drivel.domain.push.dto;

import lombok.Data;

@Data
public class AndroidNotificationDTO {
    private NotificationDTO notification;
    private String priority = "high";
}

