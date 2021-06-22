package com.heroku.spacey.error;

public class ProductOutOfStockException extends RuntimeException {

    public ProductOutOfStockException(String message) {
        super(message);
    }
}
