package com.ebiz.drivel.domain.feedback.exception;

public class EmptyFeedbackException extends RuntimeException {

    public EmptyFeedbackException() {
        super("한 개 이상 선택해주세요");
    }

    public EmptyFeedbackException(String message) {
        super(message);
    }
}
