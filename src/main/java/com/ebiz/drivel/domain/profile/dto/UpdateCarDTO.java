package com.ebiz.drivel.domain.profile.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateCarDTO {
    private String carModel;
    private Integer carCareer;
}
