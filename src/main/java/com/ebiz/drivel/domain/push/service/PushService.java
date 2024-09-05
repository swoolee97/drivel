package com.ebiz.drivel.domain.push.service;

import com.ebiz.drivel.domain.auth.application.UserDetailsServiceImpl;
import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.push.dto.AndroidNotificationDTO;
import com.ebiz.drivel.domain.push.dto.FcmMessage;
import com.ebiz.drivel.domain.push.dto.FcmMessage.Message;
import com.ebiz.drivel.domain.push.dto.FcmMessage.Notification;
import com.ebiz.drivel.domain.push.dto.NotificationDTO;
import com.ebiz.drivel.domain.push.entity.FcmToken;
import com.ebiz.drivel.domain.push.repository.FcmTokenRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PushService {
    private final UserDetailsServiceImpl userDetailsService;
    private final FcmTokenRepository fcmTokenRepository;
    private final ObjectMapper objectMapper;
    private final OkHttpClient client = new OkHttpClient();

    @Value("${fcm.project.id}")
    private String PROJECT_ID;
    private static final String FCM_SEND_URL = "https://fcm.googleapis.com/v1/projects/%s/messages:send";
    private static final MediaType JSON_MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");

    public void sendPushMessage(String title, String body, String token)
            throws IOException {
        String message = makeMessage(title, body, token);

        RequestBody requestBody = RequestBody.create(message, JSON_MEDIA_TYPE);
        Request request = new Request.Builder()
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8")
                .post(requestBody)
                .url(String.format(FCM_SEND_URL, PROJECT_ID))
                .build();
        Response response = client.newCall(request).execute();
        response.close();
    }

    private String makeMessage(String title, String body, String token) throws JsonProcessingException {
        AndroidNotificationDTO android = new AndroidNotificationDTO();
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setTitle(title);
        notificationDTO.setBody(body);
        android.setNotification(notificationDTO);

        FcmMessage fcmMessage = FcmMessage.builder()
                .message(Message.builder()
                        .token(token)
                        .android(android)
                        .notification(Notification.builder()
                                .title(title)
                                .body(body)
                                .build())
                        .build())
                .build();

        return objectMapper.writeValueAsString(fcmMessage);
    }

    private String getAccessToken() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(
                "firebase/drivel-8a5c4-firebase-adminsdk-zgcrd-d96a42cc1b.json");

        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(classPathResource.getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));
        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }

    public void save(String token) {
        Member member = userDetailsService.getMemberByContextHolder();
        FcmToken fcmToken = FcmToken.builder()
                .memberId(member.getId())
                .token(token)
                .build();
        fcmTokenRepository.save(fcmToken);
    }
}
