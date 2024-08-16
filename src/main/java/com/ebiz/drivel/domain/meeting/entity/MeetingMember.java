package com.ebiz.drivel.domain.meeting.entity;

import com.ebiz.drivel.domain.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@Table(name = "meeting_member")
public class MeetingMember {

    @EmbeddedId
    private MeetingMemberId meetingMemberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("meetingId")
    @JoinColumn(name = "meeting_id", referencedColumnName = "id")
    private Meeting meeting;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("memberId")
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @Column(name = "is_active")
    private Boolean isActive;

    public void inActive() {
        this.isActive = false;
    }

    public enum Status {
        NONE, WAITING, JOINED
    }

}
