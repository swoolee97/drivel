package com.ebiz.drivel.domain.profile.exception;

public class ProfileException extends RuntimeException {

    public ProfileException(String message) {
        super(message);
    }

    public static ProfileException userNotFound() {
        return new ProfileException(ExceptionMessage.USER_NOT_FOUND);
    }
}