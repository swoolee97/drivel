package com.ebiz.drivel.domain.feedback.exception;

public class DuplicateFeedbackException extends RuntimeException {

    public DuplicateFeedbackException() {
        super("이미 피드백한 유저입니다");
    }

    public DuplicateFeedbackException(String message) {
        super(message);
    }
}
