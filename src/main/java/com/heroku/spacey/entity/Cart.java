package com.heroku.spacey.entity;

import lombok.Data;

@Data
public class Cart {
    private long cartId;
    private long userId;
    private double overallPrice;
}
