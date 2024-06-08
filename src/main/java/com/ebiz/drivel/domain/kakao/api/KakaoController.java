package com.ebiz.drivel.domain.kakao.api;

import com.ebiz.drivel.domain.auth.dto.SignInDTO;
import com.ebiz.drivel.domain.kakao.dto.KakaoTokenResponse;
import com.ebiz.drivel.domain.kakao.dto.KakaoUserInfoResponse;
import com.ebiz.drivel.domain.kakao.service.KakaoService;
import com.ebiz.drivel.global.dto.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/kakao")
public class KakaoController {

    private final KakaoService kakaoService;

    @GetMapping("/callback")
    public ResponseEntity<SuccessResponse> kakaoCallback(@RequestParam("code") String code) {
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("성공")
                .build());
    }

    @GetMapping("/login")
    public ResponseEntity<SuccessResponse> loginWithKakao(@RequestParam("code") String code) {
        KakaoTokenResponse kakaoTokenResponse = kakaoService.getToken(code);
        KakaoUserInfoResponse userInfoResponse = kakaoService.getUserInfo(
                kakaoTokenResponse.getAccess_token());
        SignInDTO signInDTO = kakaoService.loginWithKakao(userInfoResponse.getKakao_account().getEmail());
        return ResponseEntity.ok()
                .body(SuccessResponse.builder()
                        .data(signInDTO)
                        .build());
    }
}
