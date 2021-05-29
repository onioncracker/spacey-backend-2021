package com.heroku.spacey.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
public class Product {
    private int id;
    private String name;
    private Date createdDate;
    private String productSex;
    private BigDecimal price;
    private String photo;
    private String description;
    private double discount;
    private Boolean isAvailable;

    private Material material;
    private int materialId;

    private Category category;
    private int categoryId;

    private ProductDetails productDetails;
    private int productDetailsId;
}
