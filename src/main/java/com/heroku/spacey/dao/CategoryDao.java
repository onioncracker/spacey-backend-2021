package com.heroku.spacey.dao;

import com.heroku.spacey.entity.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> getAllCategories();

    Category getById(Long id);

    Long insert(Category category);

    void update(Category category);

    void delete(Long id);
}
