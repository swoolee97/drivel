package com.ebiz.drivel.domain.member.dto;

import com.ebiz.drivel.domain.member.entity.MemberTheme;
import com.ebiz.drivel.domain.member.entity.MemberThemeId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberThemeDTO {
    private Long memberId;
    private Long themeId;

    public static MemberThemeDTO fromEntity(MemberTheme memberTheme) {
        return MemberThemeDTO.builder()
                .memberId(memberTheme.getMember().getId())
                .themeId(memberTheme.getTheme().getId())
                .build();
    }

    public MemberTheme toEntity(){
        return MemberTheme.builder()
                .memberThemeId(new MemberThemeId(memberId, themeId))
                .build();
    }
}
