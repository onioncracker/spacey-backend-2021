package com.heroku.spacey.utils;

public enum Status {
    UNACTIVATED(1),
    ACTIVATED(2);

    public final int value;

    Status(int value) {
        this.value = value;
    }
}
