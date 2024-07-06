package com.ebiz.drivel.domain.auth.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.ebiz.drivel.domain.auth.constants.ExceptionMessage;
import com.ebiz.drivel.domain.auth.dto.SignUpRequest;
import com.ebiz.drivel.domain.auth.exception.DuplicatedSignUpException;
import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.member.repository.MemberRepository;
import com.ebiz.drivel.domain.member.util.NicknameGenerator;
import com.ebiz.drivel.domain.member.util.ProfileImageGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private NicknameGenerator nicknameGenerator;

    @Mock
    private ProfileImageGenerator profileImageGenerator;

    @Mock
    private PasswordEncoder passwordEncoder;

    private SignUpRequest signUpRequest;

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();  // 실제 PasswordEncoder 사용
        signUpRequest = new SignUpRequest("email", "raw_password");
    }

    @Test
    void 회원가입_테스트() {
        // Given
        String nickname = "test_nickname";
        String defaultImagePath = "default_profile_image_path";
        String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());

        Member member = Member.builder()
                .email(signUpRequest.getEmail())
                .nickname(nickname)
                .imagePath(defaultImagePath)
                .password(encodedPassword)
                .build();

        when(nicknameGenerator.generateUniqueNickname()).thenReturn(nickname);
        when(profileImageGenerator.getDefaultProfileImagePath()).thenReturn(defaultImagePath);
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        // When
        Member savedMember = authService.signUp(signUpRequest);

        // Then
        assertEquals(signUpRequest.getEmail(), savedMember.getEmail());
        assertEquals(nickname, savedMember.getNickname());
        assertEquals(defaultImagePath, savedMember.getImagePath());
        assertTrue(passwordEncoder.matches(signUpRequest.getPassword(), savedMember.getPassword()));
    }

    @Test
    void 중복된_이메일로_회원가입_테스트() {
        when(memberRepository.save(any(Member.class))).thenThrow(
                new DataIntegrityViolationException("duplicated signUp"));
        DuplicatedSignUpException exception = assertThrows(
                DuplicatedSignUpException.class, () -> authService.signUp(signUpRequest));

        assertEquals(exception.getMessage(), ExceptionMessage.DUPLICATED_SIGN_UP);
    }

}
