package com.ebiz.drivel.domain.profile.dto;

import lombok.Data;

@Data
public class BlockProfileDTO {
    private Long memberId;
    private Long blockedUserId;
}
