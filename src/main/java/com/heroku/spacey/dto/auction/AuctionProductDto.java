package com.heroku.spacey.dto.auction;

import com.heroku.spacey.dto.category.CategoryDto;
import com.heroku.spacey.dto.color.ColorDto;
import com.heroku.spacey.dto.material.MaterialDto;
import lombok.Data;

import java.util.List;

@Data
public class AuctionProductDto {
    private Long id;
    private String name;
    private String description;
    private String productSex;
    private CategoryDto productCategory;
    private ColorDto productColor;
    private List<MaterialDto> materials;
}
