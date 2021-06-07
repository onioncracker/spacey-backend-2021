package com.heroku.spacey.dao;

import com.heroku.spacey.entity.Category;

public interface CategoryDao {
    Category getById(Long id);

    Long insert(Category category);

    void update(Category category);

    void delete(Long id);
}
