package com.heroku.spacey.dto.product;

import com.heroku.spacey.dto.category.CategoryDto;
import com.heroku.spacey.dto.material.MaterialDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddProductDto {
    private String name;
    private String productSex;
    private BigDecimal price;
    private Double discount;
    private String photo;
    private String description;
    private Boolean isAvailable;
    private AddProductDetailDto productDetails;
    private CategoryDto category;
    private MaterialDto material;
}
