package com.ebiz.drivel.domain.member.util;

import com.ebiz.drivel.domain.member.application.MemberService;
import jakarta.annotation.PostConstruct;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NicknameGenerator {
    private static final String NICKNAME_PREFIX = "user";
    private static final int NICKNAME_SUFFIX_RANGE = 1_000_000;
    private final MemberService memberService;
    private static MemberService staticMemberService;

    @PostConstruct
    public void init() {
        staticMemberService = memberService;
    }

    public String generateUniqueNickname() {
        while (true) {
            String randomNickname = generateRandomNickname();
            if (!isNicknameDuplicate(randomNickname)) {
                return randomNickname;
            }
        }
    }

    private static String generateRandomNickname() {
        Random random = new Random();
        int randomNumber = random.nextInt(NICKNAME_SUFFIX_RANGE);
        return NICKNAME_PREFIX + randomNumber;
    }

    private static boolean isNicknameDuplicate(String randomNickname) {
        return staticMemberService.isExistsByNickname(randomNickname);
    }

}
