package com.ebiz.drivel.domain.meeting.dto;

import com.ebiz.drivel.domain.meeting.entity.MeetingJoinRequest;
import com.ebiz.drivel.domain.member.entity.Member;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class JoinRequestedMemberInfoDTO extends MeetingMemberInfoDTO {

    private Long requestId;

    public static List<MeetingMemberInfoDTO> from(List<MeetingJoinRequest> requests) {
        return requests.stream().map(request -> {
            Member member = request.getMember();
            return JoinRequestedMemberInfoDTO.builder()
                    .memberId(member.getId())
                    .requestId(request.getId())
                    .imagePath(member.getImagePath())
                    .nickname(member.getNickname())
                    .description(member.getDescription())
                    .build();
        }).collect(Collectors.toList());
    }
}
