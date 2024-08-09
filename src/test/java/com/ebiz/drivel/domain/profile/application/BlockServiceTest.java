package com.ebiz.drivel.domain.profile.application;

import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.member.repository.MemberRepository;
import com.ebiz.drivel.domain.profile.dto.BlockProfileDTO;
import com.ebiz.drivel.domain.profile.repository.BlockRepository;
import com.ebiz.drivel.domain.profile.service.BlockService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BlockServiceTest {

    @InjectMocks
    private BlockService blockService;

    @Mock
    private BlockRepository blockRepository;

    @Mock
    private MemberRepository memberRepository;

    public BlockServiceTest(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBlockUser(){
        Long userId = 1L;
        Long blockedUserId = 2L;
        BlockProfileDTO dto = new BlockProfileDTO();
        dto.setBlockedUserId(blockedUserId);

        Member user = new Member();
        Member blockedUser = new Member();

        when(memberRepository.findById(userId)).thenReturn(Optional.of(user));
        when(memberRepository.findById(blockedUserId)).thenReturn(Optional.of(blockedUser));

        blockService.blockUser(userId, dto);

        verify(blockRepository).save(argThat(block -> block.getUser().equals(user) && block.getBlockedUser().equals(blockedUser)));
    }

    @Test
    @Transactional
    public void testUnblockUser(){
        Long userId = 1L;
        Long blockedUserId = 2L;
        Member user = new Member();
        Member blockedUser = new Member();

        when(memberRepository.findById(userId)).thenReturn(Optional.of(user));
        when(memberRepository.findById(blockedUserId)).thenReturn(Optional.of(blockedUser));

        blockService.unblockUser(userId, blockedUserId);

        verify(blockRepository).deleteByUserAndBlockedUser(user, blockedUser);
    }
}
