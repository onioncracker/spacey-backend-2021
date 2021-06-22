package com.heroku.spacey.dto.order;

import lombok.Data;

@Data
public class ProductCheckoutDto {
    private String productName;
    private String color;
    private String sizeName;
    private String photo;

    private int amount;
    private float sum;
}
