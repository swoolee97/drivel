package com.ebiz.drivel.domain.profile.service;

import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.member.repository.MemberRepository;
import com.ebiz.drivel.domain.profile.dto.BlockProfileDTO;
import com.ebiz.drivel.domain.profile.entity.Block;
import com.ebiz.drivel.domain.profile.exception.ProfileException;
import com.ebiz.drivel.domain.profile.repository.BlockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BlockService {
    private final BlockRepository blockRepository;
    private final MemberRepository memberRepository;

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(ProfileException::userNotFound);
    }

    public void blockUser(BlockProfileDTO blockProfileDTO) {
        Long userId = blockProfileDTO.getMemberId();
        Long blockedUserId = blockProfileDTO.getBlockedUserId();

        Member user = findMemberById(userId);
        Member blockedUser = findMemberById(blockedUserId);

        Block block = Block.builder()
                .member(user)
                .blockedMember(blockedUser)
                .build();
        blockRepository.save(block);
    }

    @Transactional
    public void unblockUser(Long userId, Long blockedUserId) {
        Member user = findMemberById(userId);
        Member blockedUser = findMemberById(blockedUserId);

        blockRepository.deleteByMemberAndBlockedMember(user, blockedUser);
    }

    public boolean isUserBlocked(Long userId, Long blockedUserId) {
        Member user = findMemberById(userId);
        Member blockedUser = findMemberById(blockedUserId);

        return blockRepository.existsByMemberAndBlockedMember(user, blockedUser);
    }
}
