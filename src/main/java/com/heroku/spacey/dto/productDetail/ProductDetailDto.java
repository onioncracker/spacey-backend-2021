package com.heroku.spacey.dto.productDetail;

import lombok.Data;

@Data
public class ProductDetailDto {
    private Long id;
    private Long productId;
    private String color;
    private String sizeProduct;
    private Long amount;
}
