package com.ebiz.drivel.domain.auth.dto;

import lombok.Builder;

@Builder
public class SignInResponse {
    private String nickname;
    private String accessToken;
    private String refreshToken;
}
