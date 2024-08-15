package com.ebiz.drivel.domain.chat;

import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final SimpMessagingTemplate template;
    private final MemberRepository memberRepository;

    public void sendMessage(ChatMessageDTO message, Long meetingId) {
        Member member = memberRepository.findById(message.getSenderId()).orElseThrow(null);
        message.initialize(member);

        template.convertAndSend("/sub/meeting/" + meetingId, message);

        ChatMessage chatMessage = ChatMessage.builder()
                .message(message.getMessage())
                .meetingId(meetingId)
                .senderId(message.getSenderId())
                .build();
        chatMessageRepository.save(chatMessage);
    }

}
