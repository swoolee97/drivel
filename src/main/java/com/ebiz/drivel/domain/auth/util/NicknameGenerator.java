package com.ebiz.drivel.domain.auth.util;

import com.ebiz.drivel.domain.member.repository.MemberRepository;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NicknameGenerator {
    private static final String NICKNAME_PREFIX = "user";
    private static final int NICKNAME_SUFFIX_RANGE = 1_000_000;
    private final MemberRepository memberRepository;

    public String generateUniqueNickname() {
        while (true) {
            String randomNickname = generateRandomNickname();
            if (!isNicknameDuplicate(randomNickname)) {
                return randomNickname;
            }
        }
    }

    private String generateRandomNickname() {
        Random random = new Random();
        int randomNumber = random.nextInt(NICKNAME_SUFFIX_RANGE);
        return NICKNAME_PREFIX + randomNumber;
    }

    private boolean isNicknameDuplicate(String randomNickname) {
        return memberRepository.existsByNickname(randomNickname);
    }

}
