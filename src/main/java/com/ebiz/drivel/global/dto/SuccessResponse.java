package com.ebiz.drivel.global.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SuccessResponse {
    private String message;
    private Object data;
}
