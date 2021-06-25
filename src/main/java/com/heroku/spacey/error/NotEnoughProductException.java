package com.heroku.spacey.error;

public class NotEnoughProductException extends RuntimeException {
    public NotEnoughProductException(String errorMessage) {
        super(errorMessage);
    }
}
