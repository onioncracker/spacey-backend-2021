package com.heroku.spacey.entity;

import lombok.Data;

@Data
public class ProductToOrder {
    private Long orderId;
    private Long productId;
    private int amount;
    private float sum;
}
