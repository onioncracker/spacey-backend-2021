package com.heroku.spacey.dto.product;

import lombok.Data;

@Data
public class AddProductDto {
    private String categoryName;
    private String name;
    private String material;
    private String sex;
    private String size;
    private String color;
    private Integer amount;
    private Double price;
    private Double discount;

    public AddProductDto(String categoryName, String name, String material,
                         String sex, String size, String color,
                         Integer amount, Double price) {
        this.categoryName = categoryName;
        this.name = name;
        this.material = material;
        this.sex = sex;
        this.size = size;
        this.color = color;
        this.amount = amount;
        this.price = price;
    }
}
