package com.ebiz.drivel.domain.member.application;

import com.ebiz.drivel.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public boolean isExistsByNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

}
