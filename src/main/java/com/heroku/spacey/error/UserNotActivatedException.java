package com.heroku.spacey.error;

public class UserNotActivatedException extends RuntimeException {
    public UserNotActivatedException(String errorMessage) {
        super(errorMessage);
    }
}
