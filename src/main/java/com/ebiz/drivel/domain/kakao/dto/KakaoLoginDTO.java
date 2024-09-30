package com.ebiz.drivel.domain.kakao.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoLoginDTO {
    private String fcmToken;
    private String kakaoAccessToken;
}
