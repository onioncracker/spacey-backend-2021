package com.heroku.spacey.dao.common;

import com.heroku.spacey.dao.category.CategoryDaoImpl;
import com.heroku.spacey.dao.material.MaterialDaoImpl;
import com.heroku.spacey.dao.product.ProductDaoImpl;
import com.heroku.spacey.dao.productDetail.ProductDetailDaoImpl;

public interface UnitOfWork {
    CategoryDaoImpl getCategoryDao();

    MaterialDaoImpl getMaterialDao();

    ProductDaoImpl getProductDao();

    ProductDetailDaoImpl getProductDetailDao();

    void commit();
}
