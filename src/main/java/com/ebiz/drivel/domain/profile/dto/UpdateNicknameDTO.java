package com.ebiz.drivel.domain.profile.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateNicknameDTO {
    private String nickname;
    private String description;
}
