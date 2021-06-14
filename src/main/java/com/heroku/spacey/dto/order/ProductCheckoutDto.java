package com.heroku.spacey.dto.order;

import lombok.Data;

@Data
public class ProductCheckoutDto {
    private Long productId;
    private String productName;

    private String color;
    private Long colorId;

    private String sizeName;
    private Long sizeId;

    private String photo;

    private int amount;
    private float sum;
}
