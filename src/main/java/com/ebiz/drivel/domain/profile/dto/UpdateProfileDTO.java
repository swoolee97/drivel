package com.ebiz.drivel.domain.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileDTO {
    private List<Long> styleIds;
    private List<Long> themeIds;
    private List<Long> togetherIds;
}
