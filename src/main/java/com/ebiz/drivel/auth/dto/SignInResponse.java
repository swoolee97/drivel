package com.ebiz.drivel.auth.dto;

import lombok.Builder;

@Builder
public class SignInResponse {
    private String nickname;
    private String accessToken;
    private String refreshToken;
}
