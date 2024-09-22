package com.ebiz.drivel.domain.meeting.exception;

public class AlreadyInactiveMeetingException extends RuntimeException {

    public AlreadyInactiveMeetingException() {
        super("이미 종료된 모임입니다");
    }

    public AlreadyInactiveMeetingException(String message) {
        super(message);
    }
}
