package com.ebiz.drivel.domain.chat;

import com.ebiz.drivel.domain.auth.application.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {

    private final JwtProvider jwtProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                (UsernamePasswordAuthenticationToken) message.getHeaders().get("simpUser");
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String accessToken = accessor.getFirstNativeHeader("accessToken");
            if (accessToken == null) {
                throw new IllegalStateException("토큰을 찾을 수 없습니다");
            }
            if (!jwtProvider.validateToken(accessToken)) {
                // 에러 던지기
                log.info("유효하지 않은 토큰");
            }
        } else if (StompCommand.UNSUBSCRIBE.equals(accessor.getCommand())) {
            handleUnsubscribe(accessor);
        } else if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
            handleSubscribe(accessor);
        }

        String email = accessor.getFirstNativeHeader("email");

        return message;
    }

    private void handleUnsubscribe(StompHeaderAccessor accessor) {

    }

    private void handleSubscribe(StompHeaderAccessor accessor) {
//        String destination = accessor.getFirstNativeHeader("destination");
//        if (destination.startsWith(CHAT_ROOM_DESTINATION)) {
//            String roomId = destination.substring(CHAT_ROOM_DESTINATION.length());
//            String email = accessor.getFirstNativeHeader("email");
//            userService.addActiveUserInChatRoom(roomId, email);
//        } else if (destination.startsWith(NEW_MESSAGE_DESTINATION)) {
//            String email = destination.substring(NEW_MESSAGE_DESTINATION.length());
//            setOperations.add(ONLINE_USER_PREFIX, email);
//        }
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {

    }

    @Override
    public boolean preReceive(MessageChannel channel) {
        System.out.println(channel);
        return true;
    }

}

