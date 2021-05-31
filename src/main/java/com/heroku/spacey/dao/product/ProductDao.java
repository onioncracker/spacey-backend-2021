package com.heroku.spacey.dao.product;

import com.heroku.spacey.entity.Product;

import java.util.List;

public interface ProductDao {
    Product getById(int id);

    List<Product> getByIdForDelete(int id);

    boolean isExist(int id);

    int insert(Product product);

    void update(Product product);

    void delete(int id);

    void remove(int id);

    void addMaterialToProduct(int materialId, int productId);
}