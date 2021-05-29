package com.heroku.spacey.entity;

import lombok.Data;

@Data
public class ProductDetails {
    private int id;
    private int productId;
    private String color;
    private String sizeProduct;
    private int amount;
}
