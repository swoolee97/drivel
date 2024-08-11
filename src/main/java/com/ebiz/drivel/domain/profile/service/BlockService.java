package com.ebiz.drivel.domain.profile.service;

import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.member.repository.MemberRepository;
import com.ebiz.drivel.domain.profile.dto.BlockProfileDTO;
import com.ebiz.drivel.domain.profile.entity.Block;
import com.ebiz.drivel.domain.profile.repository.BlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BlockService {
    private final BlockRepository blockRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public BlockService(BlockRepository blockRepository, MemberRepository memberRepository) {
        this.blockRepository = blockRepository;
        this.memberRepository = memberRepository;
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
    }

    public void blockUser(BlockProfileDTO blockProfileDTO) {
        Long userId = blockProfileDTO.getUserId();
        Long blockedUserId = blockProfileDTO.getBlockedUserId();

        Member user = findMemberById(userId);
        Member blockedUser = findMemberById(blockedUserId);

        Block block = new Block(user, blockedUser);
        blockRepository.save(block);
    }

    @Transactional
    public void unblockUser(Long userId, Long blockedUserId) {
        Member user = findMemberById(userId);
        Member blockedUser = findMemberById(blockedUserId);

        blockRepository.deleteByUserAndBlockedUser(user, blockedUser);
    }

    public boolean isUserBlocked(Long userId, Long blockedUserId) {
        Member user = findMemberById(userId);
        Member blockedUser = findMemberById(blockedUserId);

        return blockRepository.existsByUserAndBlockedUser(user, blockedUser);
    }
}
