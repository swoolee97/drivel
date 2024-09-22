package com.ebiz.drivel.domain.member.api;

import com.ebiz.drivel.domain.member.application.MemberService;
import com.ebiz.drivel.domain.member.dto.MemberScoreDTO;
import com.ebiz.drivel.global.dto.BaseResponse;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/check-nickname/{nickname}")
    public ResponseEntity<BaseResponse> checkNickname(@PathVariable String nickname) {
        memberService.checkNickname(nickname);
        return ResponseEntity.ok(BaseResponse.builder()
                .message("사용 가능한 닉네임입니다")
                .build());
    }

    @GetMapping("/check-email/{email}")
    public ResponseEntity<BaseResponse> checkEmail(@PathVariable String email) {
        memberService.checkEmail(email);
        return ResponseEntity.ok(BaseResponse.builder()
                .message("사용 가능한 이메일입니다")
                .build());
    }

    @PatchMapping("/password")
    public ResponseEntity<BaseResponse> updatePassword(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        String password = requestBody.get("password");
        memberService.updatePassword(email, password);
        return ResponseEntity.ok(BaseResponse.builder()
                .message("비밀번호가 변경되었습니다")
                .build());
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse> deleteMember() {
        memberService.deleteMember();
        return ResponseEntity.ok(BaseResponse.builder()
                .message("탈퇴 되었습니다")
                .build());
    }

    @GetMapping("/score")
    public ResponseEntity<MemberScoreDTO> getMemberScore() {
        MemberScoreDTO memberScoreDTO = memberService.getScore();
        return ResponseEntity.ok(memberScoreDTO);
    }
}
