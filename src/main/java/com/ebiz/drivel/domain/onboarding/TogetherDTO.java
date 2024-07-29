package com.ebiz.drivel.domain.onboarding;

import com.ebiz.drivel.domain.member.entity.MemberTogether;
import com.ebiz.drivel.domain.onboarding.entity.Together;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TogetherDTO {
    private Long id;
    private String displayName;

    public static TogetherDTO from(MemberTogether memberTogether) {
        Together together = memberTogether.getTogether();
        return TogetherDTO.builder()
                .id(together.getId())
                .displayName(together.getDisplayName())
                .build();
    }

}
