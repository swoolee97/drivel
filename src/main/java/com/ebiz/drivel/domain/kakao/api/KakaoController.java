package com.ebiz.drivel.domain.kakao.api;

import com.ebiz.drivel.domain.kakao.dto.KakaoTokenResponse;
import com.ebiz.drivel.domain.kakao.dto.KakaoUserInfoResponse;
import com.ebiz.drivel.domain.kakao.service.KakaoService;
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
