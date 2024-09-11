<<<<<<<< HEAD:src/main/java/com/ebiz/drivel/domain/block/repository/BlockRepository.java
package com.ebiz.drivel.domain.block.repository;
========
package com.ebiz.drivel.domain.block;
>>>>>>>> 5e5fb8b (refactor: 신고/차단 기능 수정):src/main/java/com/ebiz/drivel/domain/block/BlockRepository.java

import com.ebiz.drivel.domain.block.entity.BlockMember;
import com.ebiz.drivel.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<BlockMember, Long> {

    void deleteByMemberAndBlockedMember(Member Member, Member blockedMember);
}
