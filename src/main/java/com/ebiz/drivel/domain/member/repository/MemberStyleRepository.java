package com.ebiz.drivel.domain.member.repository;

import com.ebiz.drivel.domain.member.entity.MemberStyle;
import com.ebiz.drivel.domain.member.entity.MemberStyleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberStyleRepository extends JpaRepository<MemberStyle, MemberStyleId> {
}
