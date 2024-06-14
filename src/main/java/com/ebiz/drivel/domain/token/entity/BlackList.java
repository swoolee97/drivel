package com.ebiz.drivel.domain.token.entity;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Builder
@Document(collection = "black_list")
public class BlackList {
    @Id
    private String _id;

    @Field
    @Indexed(unique = true)
    private String refresh_token;
}
