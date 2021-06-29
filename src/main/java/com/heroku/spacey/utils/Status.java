package com.heroku.spacey.utils;

public enum Status {
    UNACTIVATED(1),
    ACTIVATED(2),
    ACTIVE(2),
    INACTIVE(3),
    TERMINATED(4);

    private final int value;

    Status(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
