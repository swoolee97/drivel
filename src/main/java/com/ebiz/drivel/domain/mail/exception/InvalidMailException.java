package com.ebiz.drivel.domain.mail.exception;

public class InvalidMailException extends RuntimeException {
    public InvalidMailException(String message) {
        super(message);
    }
}
