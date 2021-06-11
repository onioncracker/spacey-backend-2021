package com.heroku.spacey.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Data
public class Product {
    private Long id;
    private List<Material> materials;
    private List<Size> sizes;

    private Category productCategory;
    private Long categoryId;

    private Color productColor;
    private Long colorId;

    private Long amount;
    private String name;
    private String photo;
    private Date createdDate;
    private String productSex;
    private BigDecimal price;
    private String description;
    private Double discount;
    private Boolean isAvailable;
    private Boolean isOnAuction;
}
