package com.heroku.spacey.dao;

import com.heroku.spacey.entity.Category;

public interface CategoryDao {
    Category getById(int id);

    int insert(Category category);

    void update(Category category);

    void delete(int id);
}
