package com.ebiz.drivel.domain.profile.dto;

import lombok.*;

import java.util.List;

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
