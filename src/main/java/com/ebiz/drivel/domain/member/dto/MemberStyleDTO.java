package com.ebiz.drivel.domain.member.dto;

import com.ebiz.drivel.domain.member.entity.MemberStyle;
import com.ebiz.drivel.domain.member.entity.MemberStyleId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberStyleDTO {
    private Long memberId;
    private Long styleId;

    public static MemberStyleDTO fromEntity(MemberStyle memberStyle) {
        return MemberStyleDTO.builder()
                .memberId(memberStyle.getMember().getId())
                .styleId(memberStyle.getStyle().getId())
                .build();
    }

    public MemberStyle toEntity() {
        return MemberStyle.builder()
                .memberStyleId(new MemberStyleId(memberId, styleId))
                .build();
    }
}
