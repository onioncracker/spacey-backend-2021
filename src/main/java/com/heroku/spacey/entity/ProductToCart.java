package com.heroku.spacey.entity;

import lombok.Data;

@Data
public class ProductToCart {
    private Long cartId;
    private Long productId;
    private Long sizeId;

    private int amount;
    private double sum;
}
