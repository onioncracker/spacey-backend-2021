package com.heroku.spacey.entity;

import lombok.Data;

@Data
public class ProductDetail {
    private Long id;
    private Long productId;
    private String color;
    private String sizeProduct;
    private Long amount;
}
