package com.ebiz.drivel.domain.chat.application;

import com.ebiz.drivel.domain.chat.dto.ChatMessageDTO;
import com.ebiz.drivel.domain.chat.entity.ChatMessage;
import com.ebiz.drivel.domain.chat.repository.ChatMessageRepository;
import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final SimpMessagingTemplate template;
    private final MemberRepository memberRepository;

    public void sendMessage(ChatMessageDTO message, Long meetingId) {
        Member member = memberRepository.findById(message.getSenderId()).orElseThrow(null);
        message.initialize(member);

        ChatMessage savedMessage = chatMessageRepository.save(ChatMessage.from(message, meetingId));

        template.convertAndSend("/sub/meeting/" + meetingId, ChatMessageDTO.from(savedMessage, member));
    }

    @Transactional
    public void deleteMessages(String id) {
        ChatMessage chatMessage = chatMessageRepository.findById(id).orElse(null);
        chatMessage.delete();
        chatMessageRepository.save(chatMessage);
    }

}
