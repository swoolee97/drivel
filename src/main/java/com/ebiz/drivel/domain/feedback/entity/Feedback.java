package com.ebiz.drivel.domain.feedback.entity;

import com.ebiz.drivel.domain.meeting.entity.Meeting;
import com.ebiz.drivel.domain.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@Entity
@Table(name = "feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_member_id", referencedColumnName = "id")
    private Member targetMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id", referencedColumnName = "id")
    private Meeting meeting;

    @Column(name = "is_positive")
    private boolean isPositive;

    @Column(name = "feedback_id")
    private Integer feedbackId;

}
