package com.heroku.spacey.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductForCartDto {
    private Long id;
    private Long sizeId;
    private String name;
    private String color;
    private String size;
    private String photo;
    private int amount;
    private int unavailableAmount;
    private double discount;
    private double overallPrice;
}
