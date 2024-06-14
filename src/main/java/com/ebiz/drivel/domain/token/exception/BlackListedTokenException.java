package com.ebiz.drivel.domain.token.exception;

public class BlackListedTokenException extends RuntimeException {
    public BlackListedTokenException(String message) {
        super(message);
    }
}
