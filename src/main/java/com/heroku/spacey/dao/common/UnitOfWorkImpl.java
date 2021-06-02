package com.heroku.spacey.dao.common;

import com.heroku.spacey.dao.category.CategoryDaoImpl;
import com.heroku.spacey.dao.material.MaterialDaoImpl;
import com.heroku.spacey.dao.product.ProductDaoImpl;
import com.heroku.spacey.dao.productDetail.ProductDetailDaoImpl;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class UnitOfWorkImpl implements UnitOfWork {
    private final CategoryDaoImpl categoryDao;
    private final MaterialDaoImpl materialDao;
    private final ProductDaoImpl productDao;
    private final ProductDetailDaoImpl detailDao;
    private final DataSource dataSource;
    private final Connection connection;

    @SneakyThrows
    public UnitOfWorkImpl(DataSource dataSource,
                          CategoryDaoImpl categoryDao,
                          MaterialDaoImpl materialDao,
                          ProductDaoImpl productDao,
                          ProductDetailDaoImpl detailDao) {
        this.dataSource = dataSource;
        this.connection = dataSource.getConnection();
        this.categoryDao = categoryDao;
        this.materialDao = materialDao;
        this.productDao = productDao;
        this.detailDao = detailDao;
        connection.setAutoCommit(false);
    }

    public CategoryDaoImpl getCategoryDao() {
        return categoryDao;
    }

    public MaterialDaoImpl getMaterialDao() {
        return materialDao;
    }

    public ProductDaoImpl getProductDao() {
        return productDao;
    }

    public ProductDetailDaoImpl getProductDetailDao() {
        return detailDao;
    }

    @SneakyThrows
    @Override
    public void commit() {
        connection.commit();
    }
}
