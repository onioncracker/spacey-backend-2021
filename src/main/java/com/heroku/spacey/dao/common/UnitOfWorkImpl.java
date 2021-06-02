package com.heroku.spacey.dao.common;

import com.heroku.spacey.dao.category.CategoryDaoImpl;
import com.heroku.spacey.dao.material.MaterialDaoImpl;
import com.heroku.spacey.dao.product.ProductDaoImpl;
import com.heroku.spacey.dao.productDetail.ProductDetailDaoImpl;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class UnitOfWorkImpl implements UnitOfWork {
    @Autowired
    private CategoryDaoImpl categoryDao;
    private MaterialDaoImpl materialDao;
    private ProductDaoImpl productDao;
    private ProductDetailDaoImpl detailDao;
    private final DataSource dataSource;
    private final Connection connection;

    @SneakyThrows
    public UnitOfWorkImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.connection = dataSource.getConnection();
        connection.setAutoCommit(false);
    }

    public CategoryDaoImpl getCategoryDao() {
//        if (categoryDao == null) {
//            categoryDao = new CategoryDaoImpl(dataSource);
//        }
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

    @SneakyThrows
    @Override
    public void commit() {
        connection.commit();
    }
}
