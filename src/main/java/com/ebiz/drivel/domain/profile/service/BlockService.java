package com.ebiz.drivel.domain.profile.service;

import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.member.repository.MemberRepository;
import com.ebiz.drivel.domain.profile.dto.BlockProfileDTO;
import com.ebiz.drivel.domain.profile.entity.Block;
import com.ebiz.drivel.domain.profile.exception.ProfileException;
import com.ebiz.drivel.domain.profile.repository.BlockRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BlockService {
    private final BlockRepository blockRepository;
    private final MemberRepository memberRepository;

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> ProfileException.userNotFound());
    }

    private Long getCurrentMemberId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Optional<Member> memberOptional = memberRepository.findMemberByEmail(email);

        if(memberOptional.isPresent()) {
            return memberOptional.get().getId();
        } else{
            throw new UsernameNotFoundException("유저 이메일을 찾을 수 없습니다.");
        }
    }


    public void blockMember(BlockProfileDTO blockProfileDTO) {
        Long memberId = getCurrentMemberId();
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
    public void unblockMember(Long blockedMemberId) {
        Long memberId = getCurrentMemberId();
        Member member = findMemberById(memberId);
        Member blockedMember = findMemberById(blockedMemberId);

        blockRepository.deleteByMemberAndBlockedMember(member, blockedMember);
    }
}
