package com.ebiz.drivel.domain.auth.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignInRequest {
    @NotNull
    private String email;
    @NotNull
    private String password;
    private String fcmToken;
}
