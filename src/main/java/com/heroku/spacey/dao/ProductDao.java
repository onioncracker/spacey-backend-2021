package com.heroku.spacey.dao;

import com.heroku.spacey.entity.Product;

public interface ProductDao {
    Product get(Long id);

    boolean isExist(Long id);

    Long insert(Product product);

    void update(Product product);

    void delete(Long id);

    void deactivate(Long id);

    void addMaterialToProduct(Long materialId, Long productId);
}