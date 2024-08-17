package com.ebiz.drivel.domain.block.service;

import com.ebiz.drivel.domain.auth.application.UserDetailsServiceImpl;
import com.ebiz.drivel.domain.block.dto.BlockMemberDTO;
import com.ebiz.drivel.domain.block.entity.BlockMember;
import com.ebiz.drivel.domain.block.repository.BlockRepository;
import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.member.exception.MemberNotFoundException;
import com.ebiz.drivel.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlockService {
    private final BlockRepository blockRepository;
    private final MemberRepository memberRepository;
    private final UserDetailsServiceImpl userDetailsService;

    public void blockMember(BlockMemberDTO blockMemberDTO) {

        Member member = userDetailsService.getMemberByContextHolder();
        Member blockedMember = memberRepository.findById(blockMemberDTO.getTargetMemberId())
                .orElseThrow(() -> new MemberNotFoundException("찾을 수 없는 유저입니다"));

        BlockMember blockMember = BlockMember.builder()
                .member(member)
                .blockedMember(blockedMember)
                .build();

        blockRepository.save(blockMember);
    }

}
