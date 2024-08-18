package com.ebiz.drivel.domain.meeting.exception;

public class MeetingMemberNotFoundException extends RuntimeException {
    public MeetingMemberNotFoundException(String message) {
        super(message);
    }
}
