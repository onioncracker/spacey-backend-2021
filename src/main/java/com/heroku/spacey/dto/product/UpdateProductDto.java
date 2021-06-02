package com.heroku.spacey.dto.product;

import com.heroku.spacey.dto.category.CategoryDto;
import com.heroku.spacey.dto.material.MaterialDto;
import java.math.BigDecimal;

import com.heroku.spacey.dto.productDetail.UpdateProductDetailDto;
import lombok.Data;

@Data
public class UpdateProductDto {
    private String name;
    private String productSex;
    private BigDecimal price;
    private Double discount;
    private String photo;
    private String description;
    private Boolean isAvailable;
    private UpdateProductDetailDto productDetails;
    private CategoryDto category;
    private MaterialDto material;
}
