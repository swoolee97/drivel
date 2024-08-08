package com.ebiz.drivel.domain.member.api;

import com.ebiz.drivel.domain.member.application.MemberService;
import com.ebiz.drivel.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @PutMapping("/check-nickname")
    public ResponseEntity<String> checkNickname(@RequestBody Map<String, String> requestBody) {
        String nickname = requestBody.get("nickname");
        return memberService.checkNickname(nickname);
    }
}
