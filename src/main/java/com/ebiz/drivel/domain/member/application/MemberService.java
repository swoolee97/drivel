package com.ebiz.drivel.domain.member.application;

import com.ebiz.drivel.domain.auth.application.UserDetailsServiceImpl;
import com.ebiz.drivel.domain.auth.exception.DuplicatedResourceException;
import com.ebiz.drivel.domain.auth.exception.InvalidFormException;
import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final UserDetailsServiceImpl userDetailsService;
    private final MemberRepository memberRepository;

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

}

