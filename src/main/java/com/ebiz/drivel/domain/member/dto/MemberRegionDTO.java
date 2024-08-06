package com.ebiz.drivel.domain.member.dto;

import com.ebiz.drivel.domain.member.entity.MemberRegion;
import com.ebiz.drivel.domain.member.entity.MemberRegionId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberRegionDTO {
    private Long memberId;
    private Long regionId;

    public static MemberRegionDTO fromEntity(MemberRegion memberRegion) {
        return MemberRegionDTO.builder()
                .memberId(memberRegion.getMember().getId())
                .regionId(memberRegion.getRegion().getId())
                .build();
    }

    public MemberRegion toEntity(){
        return MemberRegion.builder()
                .memberRegionId(new MemberRegionId(memberId, regionId))
                .build();
    }
}
