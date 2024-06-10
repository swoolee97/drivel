package com.ebiz.drivel.domain.mail.exception;

public class WrongAuthenticationCodeException extends RuntimeException {
    public WrongAuthenticationCodeException(String message) {
        super(message);
    }
}
