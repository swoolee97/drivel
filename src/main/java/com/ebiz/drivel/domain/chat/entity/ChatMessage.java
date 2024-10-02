package com.ebiz.drivel.domain.chat.entity;

import com.ebiz.drivel.domain.chat.dto.ChatMessageDTO;
import jakarta.persistence.Id;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "chat_message")
public class ChatMessage {

    @Id
    private String _id;

    @Field
    private String message;

    @Field(value = "meeting_id")
    private Long meetingId;

    @Field(value = "sender_id")
    private Long senderId;

    @Field(value = "send_at")
    @Builder.Default
    private Date sendAt = new Date();

    @Field(value = "is_deleted")
    @Builder.Default
    private Boolean isDeleted = false;

    public void delete() {
        this.isDeleted = true;
    }

    public static ChatMessage from(ChatMessageDTO chatMessageDTO, Long meetingId) {
        return ChatMessage.builder()
                .message(chatMessageDTO.getMessage())
                .meetingId(meetingId)
                .senderId(chatMessageDTO.getSenderId())
                .build();
    }

}
