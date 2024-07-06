package com.ebiz.drivel.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpRequest {
    private String email;
    private String password;
}
