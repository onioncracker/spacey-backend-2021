package com.heroku.spacey.config;

import com.heroku.spacey.dao.category.CategoryDao;
import com.heroku.spacey.dao.category.CategoryDaoImpl;
import com.heroku.spacey.dao.material.MaterialDao;
import com.heroku.spacey.dao.material.MaterialDaoImpl;
import com.heroku.spacey.dao.product.ProductDao;
import com.heroku.spacey.dao.product.ProductDaoImpl;
import com.heroku.spacey.dao.productDetail.ProductDetailDao;
import com.heroku.spacey.dao.productDetail.ProductDetailDaoImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class ApplicationConfiguration {
    private final DataSource dataSource;

    public ApplicationConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public CategoryDao categoryDao() {
        return new CategoryDaoImpl(dataSource);
    }

    @Bean
    public MaterialDao materialDao() {
        return new MaterialDaoImpl(dataSource);
    }

    @Bean
    public ProductDao productDao() {
        return new ProductDaoImpl(dataSource);
    }

    @Bean
    public ProductDetailDao productDetailDao() {
        return new ProductDetailDaoImpl(dataSource);
    }
}
