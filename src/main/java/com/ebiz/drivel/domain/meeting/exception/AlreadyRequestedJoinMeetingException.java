package com.ebiz.drivel.domain.meeting.exception;

public class AlreadyRequestedJoinMeetingException extends RuntimeException {
    public AlreadyRequestedJoinMeetingException(String message) {
        super(message);
    }
}
