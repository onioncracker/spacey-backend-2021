package com.heroku.spacey.dto.product;

import lombok.Data;

@Data
public class UpdateProductDetailDto {
    private Integer id;
    private Integer productId;
    private String color;
    private String sizeProduct;
    private Integer amount;
}
