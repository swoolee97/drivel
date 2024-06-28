package com.ebiz.drivel.domain.meeting.dto;

import com.ebiz.drivel.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MeetingMasterInfoDTO {
    private String masterImagePath;
    private String masterNickname;
    private String masterDescription;

    public static MeetingMasterInfoDTO from(Member member) {
        return MeetingMasterInfoDTO.builder()
                .masterImagePath(member.getImagePath())
                .masterNickname(member.getNickname())
                .masterDescription(member.getDescription())
                .build();
    }

}
