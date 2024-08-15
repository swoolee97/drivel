package com.ebiz.drivel.domain.chat;

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
@Document(collection = "chatMessage")
public class ChatMessage {

    @Id
    private String _id;

    @Field
    private String message;

    @Field
    private Long meetingId;

    @Field
    private Long senderId;

    @Field
    @Builder.Default
    private Date sendAt = new Date();

}
