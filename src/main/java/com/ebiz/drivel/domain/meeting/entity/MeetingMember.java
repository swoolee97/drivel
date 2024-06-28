package com.ebiz.drivel.domain.meeting.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@Table(name = "meeting_member")
public class MeetingMember {

    @EmbeddedId
    private MeetingMemberId meetingMemberId;

    @Column(name = "is_active")
    private Boolean isActive;

}
