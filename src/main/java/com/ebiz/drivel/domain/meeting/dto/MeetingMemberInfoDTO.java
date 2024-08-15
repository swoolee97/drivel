package com.ebiz.drivel.domain.meeting.dto;

import com.ebiz.drivel.domain.member.entity.Member;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class MeetingMemberInfoDTO {
    private Long memberId;
    private String imagePath;
    private String nickname;
    private String description;

    public static MeetingMemberInfoDTO from(Member member) {
        return MeetingMemberInfoDTO.builder()
                .memberId(member.getId())
                .imagePath(member.getImagePath())
                .nickname(member.getNickname())
                .description(member.getDescription())
                .build();
    }

}
