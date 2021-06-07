package com.heroku.spacey.dto.productDetail;

import lombok.Data;

@Data
public class AddProductDetailDto {
    private String sizeProduct;
    private String color;
    private Long amount;
}
