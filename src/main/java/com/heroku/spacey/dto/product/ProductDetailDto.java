package com.heroku.spacey.dto.product;

import lombok.Data;

import java.sql.Date;

@Data
public class ProductDetailDto {
    private Integer productId;
    private Integer categoryId;
    private String name;
    private Date dateAddProduct;
    private Boolean sex;
    private Double price;
    private String photo;
    private String description;
    private Double discount;
    private Boolean availability;
}
