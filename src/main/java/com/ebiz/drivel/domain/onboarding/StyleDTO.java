package com.ebiz.drivel.domain.onboarding;

import com.ebiz.drivel.domain.member.entity.MemberStyle;
import com.ebiz.drivel.domain.onboarding.entity.Style;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class StyleDTO {

    private Long id;
    private String displayName;

    public static StyleDTO from(MemberStyle memberStyle) {
        Style style = memberStyle.getStyle();
        return StyleDTO.builder()
                .id(style.getId())
                .displayName(style.getDisplayName())
                .build();
    }
}
