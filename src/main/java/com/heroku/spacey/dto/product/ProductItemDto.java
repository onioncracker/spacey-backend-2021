package com.heroku.spacey.dto.product;

import lombok.Data;

import java.util.ArrayList;

@Data
public class ProductItemDto {
    private Long id;
    private String name;
    private String sex;
    private int price;
    private String photo;
    private String description;
    private int discount;
    private boolean availability;
    private String color;
    private ArrayList<SizeDto> sizes;
    private int amount;
    private String category;
    private ArrayList<String> materials;
}
