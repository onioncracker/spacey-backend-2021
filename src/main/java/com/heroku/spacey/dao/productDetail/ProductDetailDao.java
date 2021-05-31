package com.heroku.spacey.dao.productDetail;

import com.heroku.spacey.entity.ProductDetails;

public interface ProductDetailDao {
    ProductDetails getById(int id);

    int insert(ProductDetails productDetails);

    void update(ProductDetails productDetails);

    void delete(int id);
}
