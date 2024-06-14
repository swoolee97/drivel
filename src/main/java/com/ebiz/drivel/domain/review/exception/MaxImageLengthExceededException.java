package com.ebiz.drivel.domain.review.exception;

public class MaxImageLengthExceededException extends RuntimeException {
    public MaxImageLengthExceededException(String message) {
        super(message);
    }
}
