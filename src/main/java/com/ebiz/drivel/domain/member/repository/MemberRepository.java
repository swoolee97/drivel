package com.ebiz.drivel.domain.member.repository;

import com.ebiz.drivel.domain.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByEmail(String email);
}
