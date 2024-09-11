package com.ebiz.drivel.domain.meeting.exception;

public class FullMeetingException extends RuntimeException {
    public FullMeetingException(String message) {
        super(message);
    }

    public FullMeetingException() {
        super("정원이 찼어요");
    }
}
