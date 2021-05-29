package com.heroku.spacey.dao;

import com.heroku.spacey.dao.category.CategoryDaoImpl;
import com.heroku.spacey.dao.material.MaterialDaoImpl;
import com.heroku.spacey.dao.product.ProductDaoImpl;
import com.heroku.spacey.dao.productDetail.ProductDetailDaoImpl;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class UnitOfWork {
    private CategoryDaoImpl categoryDao;
    private MaterialDaoImpl materialDao;
    private ProductDaoImpl productDao;
    private ProductDetailDaoImpl detailDao;
    private final DataSource dataSource;

    public UnitOfWork(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public CategoryDaoImpl getCategoryDao() {
        if (categoryDao == null) {
            categoryDao = new CategoryDaoImpl(dataSource);
        }
        return categoryDao;
    }

    public MaterialDaoImpl getMaterialDao() {
        if (materialDao == null) {
            materialDao = new MaterialDaoImpl(dataSource);
        }
        return materialDao;
    }

    public ProductDaoImpl getProductDao() {
        if (productDao == null) {
            productDao = new ProductDaoImpl(dataSource);
        }
        return productDao;
    }

    public ProductDetailDaoImpl getProductDetailDao() {
        if (detailDao == null) {
            detailDao = new ProductDetailDaoImpl(dataSource);
        }
        return detailDao;
    }
}
