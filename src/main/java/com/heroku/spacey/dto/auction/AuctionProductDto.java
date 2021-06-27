package com.heroku.spacey.dto.auction;

import com.heroku.spacey.dto.category.CategoryDto;
import com.heroku.spacey.dto.material.MaterialDto;
import com.heroku.spacey.entity.Color;
import lombok.Data;

import java.util.Set;

@Data
public class AuctionProductDto {
    private Long id;
    private String name;
    private String productSex;
    private String photo;
    private String description;
    private CategoryDto productCategory;
    private Color productColor;
    private Set<MaterialDto> materials;
}
