package com.ebiz.drivel.domain.kakao.service;

import com.ebiz.drivel.domain.kakao.dto.KakaoTokenResponse;
import com.ebiz.drivel.domain.kakao.dto.KakaoUserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class KakaoService {
    @Value("${KAKAO_RESTAPI_KEY}")
    private String CLIENT_ID;
    @Value("${KAKAO_REDIRECT_URI}")
    private String REDIRECT_URI;
    private final WebClient webClient;

    private static final String TOKEN_URI = "https://kauth.kakao.com/oauth/token";
    //    private static final String REDIRECT_URI = "http://localhost:8080/kakao/callback";
    private static final String GRANT_TYPE = "authorization_code";
    private static final String USER_INFO_URI = "https://kapi.kakao.com/v2/user/me";

    public KakaoTokenResponse getToken(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", GRANT_TYPE);
        params.add("redirect_uri", REDIRECT_URI);
        params.add("client_id", CLIENT_ID);
        params.add("code", code);

        Flux<KakaoTokenResponse> response = webClient.post()
                .uri(TOKEN_URI)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(params))
                .retrieve()
                .bodyToFlux(KakaoTokenResponse.class);

        return response.blockFirst();
    }

    public KakaoUserInfoResponse getUserInfo(String token) {

        Flux<KakaoUserInfoResponse> response = webClient.get()
                .uri(USER_INFO_URI)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToFlux(KakaoUserInfoResponse.class);

        return response.blockFirst();
    }

}
