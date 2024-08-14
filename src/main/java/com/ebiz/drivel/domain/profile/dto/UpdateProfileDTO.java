package com.ebiz.drivel.domain.profile.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UpdateProfileDTO {
    private List<Long> styleIds;
    private List<Long> themeIds;
    private List<Long> togetherIds;
}
