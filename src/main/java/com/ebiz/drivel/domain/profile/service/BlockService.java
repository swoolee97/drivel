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
                .orElseThrow(ProfileException::memberNotFound);
    }

    public void blockMember(BlockProfileDTO blockProfileDTO) {
        Long memberId = blockProfileDTO.getMemberId();
        Long blockedMemberId = blockProfileDTO.getBlockedMemberId();

        Member member = findMemberById(memberId);
        Member blockedMember = findMemberById(blockedMemberId);

        Block block = Block.builder()
                .member(member)
                .blockedMember(blockedMember)
                .build();
        blockRepository.save(block);
    }

    @Transactional
    public void unblockMember(Long memberId, Long blockedMemberId) {
        Member Member = findMemberById(memberId);
        Member blockedMember = findMemberById(blockedMemberId);

        blockRepository.deleteByMemberAndBlockedMember(Member, blockedMember);
    }

    public boolean isMemberBlocked(Long memberId, Long blockedMemberId) {
        Member member = findMemberById(memberId);
        Member blockedMember = findMemberById(blockedMemberId);

        return blockRepository.existsByMemberAndBlockedMember(member, blockedMember);
    }
}
