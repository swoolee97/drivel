package com.ebiz.drivel.domain.member.application;

import com.ebiz.drivel.domain.auth.application.UserDetailsServiceImpl;
import com.ebiz.drivel.domain.auth.exception.DuplicatedResourceException;
import com.ebiz.drivel.domain.auth.exception.InvalidFormException;
import com.ebiz.drivel.domain.member.dto.MemberScoreDTO;
import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.member.exception.MemberNotFoundException;
import com.ebiz.drivel.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final UserDetailsServiceImpl userDetailsService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    public boolean isExistsByNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    public void checkNickname(String nickname) {
        if (nickname == null || nickname.trim().isEmpty()) {
            throw new InvalidFormException("유효하지 않은 형식입니다");
        }

        if (memberRepository.existsByNickname(nickname)) {
            throw new DuplicatedResourceException("이미 사용중인 닉네임입니다");
        }
    }

    public void checkEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidFormException("유효하지 않은 형식입니다");
        }

        if (memberRepository.existsByEmail(email)) {
            throw new DuplicatedResourceException("이미 가입된 이메일입니다");
        }
    }

    @Transactional
    public void deleteMember() {
        Member member = userDetailsService.getMemberByContextHolder();
        member.delete();
    }

    @Transactional
    public void updatePassword(String email, String password) {
        Member member = memberRepository.findMemberByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("찾을 수 없는 유저입니다"));
        String encryptedPassword = encoder.encode(password);
        member.updatePassword(encryptedPassword);
    }

    public MemberScoreDTO getScore() {
        Member member = userDetailsService.getMemberByContextHolder();
        return MemberScoreDTO.builder()
                .score(member.getScore())
                .build();
    }

}

