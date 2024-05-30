package com.ebiz.drivel.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignInDTO {
    private String nickname;
    private String accessToken;
    private String refreshToken;
}
