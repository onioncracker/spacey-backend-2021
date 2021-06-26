package com.heroku.spacey.error;

public class NoAvailableCourierException extends RuntimeException {
    public NoAvailableCourierException(String message) {
        super(message);
    }
}
