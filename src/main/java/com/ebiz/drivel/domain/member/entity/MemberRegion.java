package com.ebiz.drivel.domain.member.entity;

import com.ebiz.drivel.domain.onboarding.entity.Region;
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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member_region")
public class MemberRegion {

    @EmbeddedId
    private MemberRegionId memberRegionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("memberId")
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("regionId")
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    private Region region;

}
