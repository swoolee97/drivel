package com.ebiz.drivel.domain.kakao.exception;

public class DuplicatedSignUpMemberException extends RuntimeException {
    public DuplicatedSignUpMemberException(String message) {
        super(message);
    }
}
