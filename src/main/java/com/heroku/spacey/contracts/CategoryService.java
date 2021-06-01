package com.heroku.spacey.contracts;

import com.heroku.spacey.dto.category.CategoryDto;

public interface CategoryService {
    CategoryDto getById(int id);

    void addCategory(CategoryDto categoryDto);

    void updateCategory(CategoryDto CategoryDto, int id);

    void deleteCategory(int id);
}
