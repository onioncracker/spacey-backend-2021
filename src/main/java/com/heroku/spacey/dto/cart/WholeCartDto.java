package com.heroku.spacey.dto.cart;

import com.heroku.spacey.entity.ProductToCart;
import lombok.Data;

import java.util.Set;

@Data
public class WholeCartDto {
    private double overallPrice;
    private Set<ProductToCart> products;
}
