package com.ebiz.drivel.domain.push.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PushRequest {
    private String title;
    private String body;
}
