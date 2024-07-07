package com.ebiz.drivel.domain.member.repository;

import com.ebiz.drivel.domain.member.entity.MemberRegion;
import com.ebiz.drivel.domain.member.entity.MemberRegionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRegionRepository extends JpaRepository<MemberRegion, MemberRegionId> {
}
