package com.heroku.spacey.dao;

import com.heroku.spacey.dao.category.CategoryDaoImpl;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class UnitOfWork {
    private CategoryDaoImpl categoryDao;
    private DataSource dataSource;

    public UnitOfWork(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public CategoryDaoImpl getCategoryDao() {
        if (categoryDao == null) {
            categoryDao = new CategoryDaoImpl(dataSource);
        }
        return categoryDao;
    }
}
