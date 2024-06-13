package com.ebiz.drivel.domain.festival.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorResponse {

    private cmmMsgHeader cmmMsgHeader;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class cmmMsgHeader {
        private String errMsg;
        private String returnAuthMsg;
        private String returnReasonCode;
    }
}
