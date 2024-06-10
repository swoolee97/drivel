package com.ebiz.drivel.domain.mail.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthCodeCheckDTO {
    @NotNull
    String email;
    @NotNull
    String randomCode;
}
