package com.ebiz.drivel.domain.block;

import com.ebiz.drivel.domain.auth.application.UserDetailsServiceImpl;
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
        Member targetMember = memberRepository.findById(blockMemberDTO.getTargetMemberId())
                .orElseThrow(() -> new MemberNotFoundException("찾을 수 없는 유저입니다"));

        BlockMember blockMember = BlockMember.builder()
                .member(member)
                .targetMember(targetMember)
                .build();

        blockRepository.save(blockMember);
    }

}
