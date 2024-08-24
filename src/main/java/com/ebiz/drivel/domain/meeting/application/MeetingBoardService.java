package com.ebiz.drivel.domain.meeting.application;

import com.ebiz.drivel.domain.chat.dto.ChatMessageDTO;
import com.ebiz.drivel.domain.chat.entity.ChatMessage;
import com.ebiz.drivel.domain.chat.repository.ChatMessageRepository;
import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.member.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeetingBoardService {

    private final MemberRepository memberRepository;
    private final ChatMessageRepository chatMessageRepository;

    public List<ChatMessageDTO> getMessages(Long meetingId, String lastMessageId) {
        Pageable pageable = PageRequest.of(0, 50, Sort.by(Sort.Direction.DESC, "_id"));
        List<ChatMessage> messages;
        if (lastMessageId.equals("-1")) {
            messages = chatMessageRepository.findLatestMessages(meetingId, pageable);
        } else {
            messages = chatMessageRepository.findPreviousMessages(lastMessageId, meetingId, pageable);
        }
        return messages.stream().map(message -> {
            Member member = memberRepository.findById(message.getSenderId()).get();
            return ChatMessageDTO.from(message, member);
        }).toList();
    }

}
