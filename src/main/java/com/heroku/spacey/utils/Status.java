package com.heroku.spacey.utils;

public enum Status {
    UNACTIVATED(1),
    ACTIVATED(2);

    private final int value;

    Status(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
