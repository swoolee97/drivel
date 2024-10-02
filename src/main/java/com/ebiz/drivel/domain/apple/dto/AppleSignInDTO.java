package com.ebiz.drivel.domain.apple.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AppleSignInDTO {
    private String email;
    private String fcmToken;
}
