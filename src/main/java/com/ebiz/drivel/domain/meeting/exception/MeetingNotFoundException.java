package com.ebiz.drivel.domain.meeting.exception;

public class MeetingNotFoundException extends RuntimeException {
    public MeetingNotFoundException(String message) {
        super(message);
    }
}
