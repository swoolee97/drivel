package com.ebiz.drivel.domain.token.exception;

public class DifferentRefreshTokenException extends RuntimeException {
    public DifferentRefreshTokenException(String message) {
        super(message);
    }
}
