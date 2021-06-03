package com.heroku.spacey.dao;

import com.heroku.spacey.entity.ProductDetail;

public interface ProductDetailDao {
    ProductDetail getById(int id);

    int insert(ProductDetail productDetail);

    void update(ProductDetail productDetail);

    void delete(int id);
}
