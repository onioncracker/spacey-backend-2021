package com.heroku.spacey.dto.cart;

import lombok.Data;

@Data
public class ProductForUnauthorizedCart {
    private Long id;
    private Long sizeId;
    private String name;
    private String color;
    private String size;
    private String photo;
    private int quantity;
    private double price;
}
