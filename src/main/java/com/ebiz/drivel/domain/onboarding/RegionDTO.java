package com.ebiz.drivel.domain.onboarding;

import com.ebiz.drivel.domain.member.entity.MemberRegion;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegionDTO {
    private Long id;
    private String displayName;

    public static RegionDTO from(MemberRegion memberRegion) {
        return RegionDTO.builder()
                .id(memberRegion.getRegion().getId())
                .displayName(memberRegion.getRegion().getDisplayName())
                .build();
    }

}
