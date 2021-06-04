package com.heroku.spacey.dto.product;

import lombok.Data;

import java.util.ArrayList;

@Data
public class ProductItemDto {
    private int id;
    private String name;
    private String sex;
    private int price;
    private String photo;
    private String description;
    private int discount;
    private boolean availability;
    private String color;
    private String size;
    private int amount;
    private String category;
    private ArrayList<String> material;
}
