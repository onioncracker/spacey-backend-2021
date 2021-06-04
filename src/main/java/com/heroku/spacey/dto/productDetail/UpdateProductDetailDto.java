package com.heroku.spacey.dto.productDetail;

import lombok.Data;

@Data
public class UpdateProductDetailDto {
    private Integer id;
    private Integer productId;
    private String color;
    private String sizeProduct;
    private Integer amount;
}
