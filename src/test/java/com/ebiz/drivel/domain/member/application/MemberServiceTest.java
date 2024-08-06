package com.ebiz.drivel.domain.member.application;

import static javax.security.auth.callback.ConfirmationCallback.OK;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import com.ebiz.drivel.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @DisplayName("존재하는_닉네임_테스트")
    @Test
    void testIsExistsByNickname() {
        // when
        when(memberRepository.existsByNickname(any(String.class))).thenReturn(true);

        // given
        boolean exists = memberService.isExistsByNickname("exists_nickname");

        // then
        assertTrue(exists);
        verify(memberRepository, times(1)).existsByNickname("exists_nickname");
    }

    @DisplayName("존재하지_않는_닉네임_테스트")
    @Test
    void testIsNotExistsByNickname() {
        // when
        when(memberRepository.existsByNickname(any(String.class))).thenReturn(false);
        boolean exists = memberService.isExistsByNickname("nonexistent_nickname");

        // given
        assertFalse(exists);

        // then
        verify(memberRepository, times(1)).existsByNickname("nonexistent_nickname");
    }
    @DisplayName("존재하지_않는_닉네임_테스트")
    @Test
    void testCheckNicknameNotExists(){
        // when
        when(memberRepository.existsByNickname(any(String.class))).thenReturn(false);

        // given
        ResponseEntity<String> response = memberService.checkNickname("nonexistent_nickname");

        // then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("사용 가능한 닉네임입니다.", response.getBody());
        verify(memberRepository).existsByNickname("nonexistent_nickname");
    }
}
