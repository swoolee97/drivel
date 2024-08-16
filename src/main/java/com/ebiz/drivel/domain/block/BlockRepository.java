package com.ebiz.drivel.domain.block;

import com.ebiz.drivel.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<BlockMember, Long> {

    void deleteByMemberAndBlockedMember(Member Member, Member blockedMember);
}
