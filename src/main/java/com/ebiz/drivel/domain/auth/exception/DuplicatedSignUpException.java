package com.ebiz.drivel.domain.auth.exception;

public class DuplicatedSignUpException extends RuntimeException {
    public DuplicatedSignUpException(String message) {
        super(message);
    }
}
