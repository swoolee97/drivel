package com.ebiz.drivel.domain.chat.dto;

import com.ebiz.drivel.domain.chat.entity.ChatMessage;
import com.ebiz.drivel.domain.member.entity.Member;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class ChatMessageDTO {
    private String id;
    private Long senderId;
    @Setter
    private String imagePath;
    private String senderNickname;
    private String message;
    private Date sendAt;

    public void initialize(Member member) {
        this.imagePath = member.getImagePath();
        this.sendAt = new Date();
        this.senderNickname = member.getNickname();
    }

    public static ChatMessageDTO from(ChatMessage chatMessage, Member member) {
        return ChatMessageDTO.builder()
                .id(chatMessage.get_id())
                .imagePath(member.getImagePath())
                .senderNickname(member.getNickname())
                .senderId(chatMessage.getSenderId())
                .message(chatMessage.getMessage())
                .sendAt(chatMessage.getSendAt())
                .build();
    }

}
