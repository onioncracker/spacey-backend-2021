package com.heroku.spacey.dto.product;

import lombok.Data;

@Data
public class ProductForCartDto {
    private Long id;
    private String name;
    private String color;
    private String size;
    private String photo;
    private int amount;
    private double overallPrice;
}
