package com.heroku.spacey.dao.category;

import com.heroku.spacey.entity.Category;
import com.heroku.spacey.entity.Product;

public interface CategoryDao {
    Category getById(int id);
    int insert(Category category);
    Category update(Category category);
    Category delete(int id);
}
