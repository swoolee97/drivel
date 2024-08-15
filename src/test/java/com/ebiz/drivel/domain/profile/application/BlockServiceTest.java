package com.ebiz.drivel.domain.profile.application;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.member.repository.MemberRepository;
import com.ebiz.drivel.domain.profile.dto.BlockProfileDTO;
import com.ebiz.drivel.domain.profile.repository.BlockRepository;
import com.ebiz.drivel.domain.profile.service.BlockService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.annotation.Transactional;

public class BlockServiceTest {

    @InjectMocks
    private BlockService blockService;

    @Mock
    private BlockRepository blockRepository;

    @Mock
    private MemberRepository memberRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBlockUser() {
        Long userId = 1L;
        Long blockedUserId = 2L;
        BlockProfileDTO dto = new BlockProfileDTO();
        dto.setMemberId(userId);
        dto.setBlockedMemberId(blockedUserId);

        Member user = new Member();
        Member blockedUser = new Member();

        when(memberRepository.findById(userId)).thenReturn(Optional.of(user));
        when(memberRepository.findById(blockedUserId)).thenReturn(Optional.of(blockedUser));

        blockService.blockMember(dto);

        verify(blockRepository).save(argThat(block ->
                block.getMember().equals(user) &&
                        block.getBlockedMember().equals(blockedUser)));
    }

    @Test
    @Transactional
    public void testUnblockUser() {
        Long userId = 1L;
        Long blockedUserId = 2L;
        Member user = new Member();
        Member blockedUser = new Member();

        when(memberRepository.findById(userId)).thenReturn(Optional.of(user));
        when(memberRepository.findById(blockedUserId)).thenReturn(Optional.of(blockedUser));

        blockService.unblockMember(userId, blockedUserId);

        verify(blockRepository).deleteByMemberAndBlockedMember(user, blockedUser);
    }
}
