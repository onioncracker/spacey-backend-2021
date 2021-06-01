package com.heroku.spacey.dao.category;

import com.heroku.spacey.entity.Category;

public interface CategoryDao {
    Category getById(int id);

    int insert(Category category);

    boolean isExist(int id);

    void update(Category category);

    void delete(int id);
}
