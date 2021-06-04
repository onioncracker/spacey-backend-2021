package com.heroku.spacey.entity;

import lombok.Data;

@Data
public class ProductDetail {
    private Integer id;
    private Integer productId;
    private String color;
    private String sizeProduct;
    private Integer amount;
}
