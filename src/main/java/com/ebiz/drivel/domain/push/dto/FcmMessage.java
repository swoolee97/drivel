package com.ebiz.drivel.domain.push.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FcmMessage {
    private Message message;

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Message {
        private Data data;
        private AndroidNotificationDTO android;
        private Notification notification;
        private String token;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Data {
        private String id;
        private String nickName;
        private String imageAddress;
    }

    @Builder
    @Getter
    public static class Notification {
        private String title;
        private String body;
    }

}
