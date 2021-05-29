package com.heroku.spacey.dto.product;

import com.heroku.spacey.dto.category.UpdateCategoryDto;
import com.heroku.spacey.dto.material.UpdateMaterialDto;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class UpdateProductDto {
    private Integer id;
    private String name;
    private String productSex;
    private BigDecimal price;
    private Double discount;
    private String photo;
    private String description;
    private Boolean isAvailable;
    private UpdateProductDetailDto productDetails;
    private UpdateCategoryDto category;
    private UpdateMaterialDto material;
}
