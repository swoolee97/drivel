package com.ebiz.drivel.oauth.kakao.controller;

import com.ebiz.drivel.oauth.kakao.dto.KakaoTokenResponse;
import com.ebiz.drivel.oauth.kakao.dto.KakaoUserInfoResponse;
import com.ebiz.drivel.oauth.kakao.service.KakaoService;
import lombok.RequiredArgsConstructor;
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
    public void kakaoCallback(@RequestParam("code") String code) {
        KakaoTokenResponse kakaoTokenResponse = kakaoService.getToken(code);
        KakaoUserInfoResponse userInfoResponse = kakaoService.getUserInfo(
                kakaoTokenResponse.getAccess_token());
    }

}
