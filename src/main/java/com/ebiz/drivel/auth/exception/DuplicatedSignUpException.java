package com.ebiz.drivel.auth.exception;

public class DuplicatedSignUpException extends RuntimeException {
    public DuplicatedSignUpException(String message) {
        super(message);
    }
}
