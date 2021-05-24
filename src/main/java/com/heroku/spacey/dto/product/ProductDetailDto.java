package com.heroku.spacey.dto.product;

import lombok.Data;

import java.sql.Date;

@Data
public class ProductDetailDto {
    private int productId;
    private int categoryId;
    private String name;
    private Date dateAddProduct;
    private boolean sex;
    private double price;
    private String photo;
    private String description;
    private double discount;
    private boolean availability;
}
