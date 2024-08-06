package com.ebiz.drivel.domain.member.dto;

import com.ebiz.drivel.domain.member.entity.MemberTogether;
import com.ebiz.drivel.domain.member.entity.MemberTogetherId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberTogetherDTO {
    private Long memberId;
    private Long togetherId;

    public static MemberTogetherDTO fromEntity(MemberTogether memberTogether) {
        return MemberTogetherDTO.builder()
                .memberId(memberTogether.getMember().getId())
                .togetherId(memberTogether.getTogether().getId())
                .build();
    }

    public MemberTogether toEntity() {
        return MemberTogether.builder()
                .memberTogetherId(new MemberTogetherId(memberId, togetherId))
                .build();
    }
}
