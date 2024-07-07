package com.ebiz.drivel.domain.member.entity;

import com.ebiz.drivel.domain.onboarding.entity.Together;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member_together")
public class MemberTogether {

    @EmbeddedId
    private MemberTogetherId memberTogetherId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("memberId")
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("togetherId")
    @JoinColumn(name = "together_id", referencedColumnName = "id")
    private Together together;

}
