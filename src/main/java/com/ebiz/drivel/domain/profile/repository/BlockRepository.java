package com.ebiz.drivel.domain.profile.repository;

import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.profile.entity.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block, Long> {
    boolean existsByUserAndBlockedUser(Member user, Member blockedUser);
    void deleteByUserAndBlockedUser(Member user, Member blockedUser);
}