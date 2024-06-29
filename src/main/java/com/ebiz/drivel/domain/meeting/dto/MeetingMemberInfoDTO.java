package com.ebiz.drivel.domain.meeting.dto;

import com.ebiz.drivel.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MeetingMemberInfoDTO {
    private Long id;
    private String imagePath;
    private String nickname;
    private String description;

    public static MeetingMemberInfoDTO from(Member member) {
        return MeetingMemberInfoDTO.builder()
                .id(member.getId())
                .imagePath(member.getImagePath())
                .nickname(member.getNickname())
                .description(member.getDescription())
                .build();
    }

}
