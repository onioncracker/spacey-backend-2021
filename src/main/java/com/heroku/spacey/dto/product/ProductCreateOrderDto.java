package com.heroku.spacey.dto.product;

import lombok.Data;

@Data
public class ProductCreateOrderDto {
    private Long productId;
    private Long sizeId;

    private int amount;
    private float sum;
}
