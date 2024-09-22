package com.ebiz.drivel.domain.meeting.exception;

public class NotMasterMemberException extends RuntimeException {
    private static String message = "모임장만 종료할 수 있어요";

    public NotMasterMemberException(String message) {
        super(message);
    }

    public NotMasterMemberException() {
        super(message);
    }
}
