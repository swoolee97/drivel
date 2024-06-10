package com.ebiz.drivel.domain.mail.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CheckCodeDTO {
    private String email;
    private String code;
}
