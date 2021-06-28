package com.heroku.spacey.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductToCart {
    private Long cartId;
    private Long productId;
    private Long sizeId;

    private int amount;
    private double sum;
    private LocalDateTime addDateTime;
}
