package com.heroku.spacey.dto.product;

import com.heroku.spacey.dto.category.CategoryDto;
import com.heroku.spacey.dto.color.ColorDto;
import com.heroku.spacey.dto.material.MaterialDto;
import com.heroku.spacey.dto.size.SizeDto;
import lombok.Data;

import java.util.List;

@Data
public class AddProductDto {
    private Long amount;
    private String name;
    private String productSex;
    private Double price;
    private Double discount;
    private String photo;
    private String description;
    private Boolean isAvailable;
    private Boolean isOnAuction;
    private CategoryDto category;
    private ColorDto color;
    private List<MaterialDto> materials;
    private List<SizeDto> sizes;
}
