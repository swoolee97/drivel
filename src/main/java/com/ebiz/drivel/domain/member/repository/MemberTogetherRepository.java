package com.ebiz.drivel.domain.member.repository;

import com.ebiz.drivel.domain.member.entity.MemberTogether;
import com.ebiz.drivel.domain.member.entity.MemberTogetherId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberTogetherRepository extends JpaRepository<MemberTogether, MemberTogetherId> {
}
