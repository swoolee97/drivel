package com.ebiz.drivel.domain.meeting.dto;

import com.ebiz.drivel.domain.meeting.entity.MeetingNotice;
import com.ebiz.drivel.domain.member.entity.Member;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MeetingNoticeResponse {
    private Long id;
    private Long writerId;
    private String writerNickname;
    private String content;
    private LocalDateTime createdAt;

    public static MeetingNoticeResponse from(MeetingNotice meetingNotice) {
        Member writer = meetingNotice.getWriter();
        return MeetingNoticeResponse.builder()
                .id(meetingNotice.getId())
                .writerId(writer.getId())
                .writerNickname(writer.getNickname())
                .content(meetingNotice.getContent())
                .createdAt(meetingNotice.getCreatedAt())
                .build();

    }

}
