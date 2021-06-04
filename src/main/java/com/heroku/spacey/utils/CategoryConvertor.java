package com.heroku.spacey.utils;

import com.heroku.spacey.dto.category.CategoryDto;
import com.heroku.spacey.entity.Category;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryConvertor {
    private final ModelMapper mapper = new ModelMapper();

    public Category adapt(CategoryDto source) {
        mapper.typeMap(CategoryDto.class, Category.class);
        return mapper.map(source, Category.class);
    }

    public CategoryDto adapt(Category source) {
        mapper.typeMap(Category.class, CategoryDto.class);
        return mapper.map(source, CategoryDto.class);
    }
}
