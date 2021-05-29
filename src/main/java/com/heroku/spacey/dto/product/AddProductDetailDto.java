package com.heroku.spacey.dto.product;

import lombok.Data;

@Data
public class AddProductDetailDto {
    private String sizeProduct;
    private String color;
    private Integer amount;
}
