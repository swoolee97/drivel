package com.ebiz.drivel.domain.member.api;

import com.ebiz.drivel.domain.member.application.MemberService;
import com.ebiz.drivel.global.dto.BaseResponse;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PutMapping("/check-nickname")
    public ResponseEntity<String> checkNickname(@RequestBody Map<String, String> requestBody) {
        String nickname = requestBody.get("nickname");
        return memberService.checkNickname(nickname);
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse> deleteMember() {
        memberService.deleteMember();

        return ResponseEntity.ok(BaseResponse.builder()
                .message("탈퇴 되었습니다.")
                .build());
    }

}
