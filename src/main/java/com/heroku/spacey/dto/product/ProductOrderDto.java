package com.heroku.spacey.dto.product;

import lombok.Data;

@Data
public class ProductOrderDto {
    private Long id;
    private String name;
    private String photo;
    private String color;
    private String size;
    private float price;
    private int amount;
}
