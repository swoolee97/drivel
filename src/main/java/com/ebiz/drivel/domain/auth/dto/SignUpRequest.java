package com.ebiz.drivel.domain.auth.dto;

import lombok.Getter;

@Getter
public class SignUpRequest {
    private String email;
    private String password;
}
