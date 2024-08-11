package com.ebiz.drivel.domain.auth.dto;

import com.ebiz.drivel.global.dto.BaseResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class SignInDTO extends BaseResponse {
    private Long id;
    private String nickname;
    private String accessToken;
    private String refreshToken;
    private boolean onboarded;
}
