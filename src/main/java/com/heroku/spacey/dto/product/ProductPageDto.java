package com.heroku.spacey.dto.product;

import lombok.Data;

@Data
public class ProductPageDto {
    private int id;
    private String photo;
    private String name;
    private String price;
    private int discount;
}
