package com.heroku.spacey.dto.product;

import com.heroku.spacey.dto.category.CategoryDto;
import com.heroku.spacey.dto.color.ColorDto;
import com.heroku.spacey.dto.material.MaterialDto;
import java.math.BigDecimal;
import java.sql.Date;

import com.heroku.spacey.dto.size.SizeDto;
import lombok.Data;

@Data
public class UpdateProductDto {
    private Long amount;
    private String name;
    private Date createdDate;
    private String productSex;
    private BigDecimal price;
    private Double discount;
    private String photo;
    private String description;
    private Boolean isAvailable;
    private CategoryDto category;
    private MaterialDto material;
    private SizeDto size;
    private ColorDto color;
}
