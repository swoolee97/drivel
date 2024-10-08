package com.ebiz.drivel.domain.meeting.dto;

import com.ebiz.drivel.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MeetingMasterInfoDTO {
    private Long id;
    private String imagePath;
    private String nickname;
    private String description;

    public static MeetingMasterInfoDTO from(Member member) {
        return MeetingMasterInfoDTO.builder()
                .id(member.getId())
                .imagePath(member.getImagePath())
                .nickname(member.getNickname())
                .description(member.getDescription())
                .build();
    }

}
