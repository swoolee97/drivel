package com.ebiz.drivel.domain.chat.dto;

import com.ebiz.drivel.domain.member.entity.Member;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class ChatMessageDTO {
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

}
