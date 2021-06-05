package com.heroku.spacey.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Data
public class Product {
    private Integer id;
    private String name;
    private Date createdDate;
    private String productSex;
    private BigDecimal price;
    private String photo;
    private String description;
    private Double discount;
    private Boolean isAvailable;
    private Boolean isOnAuction;

    private List<Material> materials;
    private Category productCategory;
    private Integer categoryId;

    private ProductDetail productDetail;
    private Integer productDetailsId;
}
