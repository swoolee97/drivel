package com.ebiz.drivel.domain.meeting.entity;

import com.ebiz.drivel.domain.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "meeting_join_request")
@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MeetingJoinRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id", referencedColumnName = "id")
    private Meeting meeting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        WAITING, REJECTED, ACCEPTED, CANCELED
    }

    public boolean isAlreadyDecidedRequest() {
        return !status.equals(Status.WAITING);
    }

    public boolean isWaitingRequest() {
        return this.status.equals(Status.WAITING);
    }

    public void cancel() {
        this.status = Status.CANCELED;
    }

    public void accept() {
        this.status = Status.ACCEPTED;
    }

    public void reject() {
        this.status = Status.REJECTED;
    }

}
