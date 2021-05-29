package com.heroku.spacey.dao.product;

import com.heroku.spacey.entity.Product;

public interface ProductDao {
    Product getById(int id);

    int insert(Product product);

    void update(Product product);

    void delete(int id);

    void addMaterialToProduct(int materialId, int productId);
}