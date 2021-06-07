package com.heroku.spacey.dao;

import com.heroku.spacey.entity.ProductDetail;

public interface ProductDetailDao {
    ProductDetail getById(Long id);

    Long insert(ProductDetail productDetail);

    void update(ProductDetail productDetail);

    void delete(Long id);
}
