package com.heroku.spacey.services;

import com.heroku.spacey.dto.category.CategoryDto;

public interface CategoryService {
    CategoryDto getById(Long id);

    void addCategory(CategoryDto categoryDto);

    void updateCategory(CategoryDto categoryDto, Long id);

    void deleteCategory(Long id);
}
