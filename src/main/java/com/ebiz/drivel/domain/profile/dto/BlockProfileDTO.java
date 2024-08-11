package com.ebiz.drivel.domain.profile.dto;

import lombok.Data;

@Data
public class BlockProfileDTO {
    private Long userId;
    private Long blockedUserId;
}
