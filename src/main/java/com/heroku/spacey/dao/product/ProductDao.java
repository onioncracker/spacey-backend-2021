package com.heroku.spacey.dao.product;

import com.heroku.spacey.entity.Product;

public interface ProductDao {
    Product getById(int id);
    int insert(Product product);
    int update(Product product);
    int delete(int id);
}