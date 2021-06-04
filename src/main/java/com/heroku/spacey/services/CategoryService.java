package com.heroku.spacey.services;

import com.heroku.spacey.dto.category.CategoryDto;

public interface CategoryService {
    CategoryDto getById(int id);

    void addCategory(CategoryDto categoryDto);

    void updateCategory(CategoryDto categoryDto, int id);

    void deleteCategory(int id);
}
