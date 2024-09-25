package com.ebiz.drivel.domain.meeting.exception;

public class WrongAgeRangeException extends RuntimeException {
    public WrongAgeRangeException() {
        super("잘못된 나이 범위입니다");
    }

    public WrongAgeRangeException(String message) {
        super(message);
    }
}
