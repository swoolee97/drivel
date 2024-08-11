package com.ebiz.drivel.domain.profile.exception;

public class ProfileException extends RuntimeException {

    public ProfileException(String message) {
        super(message);
    }

    public static ProfileException blockUserFailed() {
        return new ProfileException(ExceptionMessage.BLOCK_USER_FAILED);
    }

    public static ProfileException unblockUserFailed() {
        return new ProfileException(ExceptionMessage.UNBLOCK_USER_FAILED);
    }

    public static ProfileException reportProfileFailed() {
        return new ProfileException(ExceptionMessage.REPORT_PROFILE_FAILED);
    }

    public static ProfileException userNotFound() {
        return new ProfileException(ExceptionMessage.USER_NOT_FOUND);
    }
}
