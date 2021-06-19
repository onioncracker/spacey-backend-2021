package com.heroku.spacey.dto.product;

import com.heroku.spacey.dto.category.CategoryDto;
import com.heroku.spacey.dto.color.ColorDto;
import com.heroku.spacey.dto.material.MaterialDto;
import com.heroku.spacey.dto.size.SizeDto;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private Date createdDate;
    private String productSex;
    private BigDecimal price;
    private Double discount;
    private String photo;
    private String description;
    private Boolean isAvailable;
    private CategoryDto category;
    private ColorDto color;
    private List<MaterialDto> materials;
    private List<SizeDto> sizes;
}
