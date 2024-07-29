package com.ebiz.drivel.domain.meeting.exception;

public class MeetingJoinRequestNotFoundException extends RuntimeException {
    public MeetingJoinRequestNotFoundException(String message) {
        super(message);
    }
}
