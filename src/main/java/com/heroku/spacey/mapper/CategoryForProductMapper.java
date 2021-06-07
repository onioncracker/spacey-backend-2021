package com.heroku.spacey.mapper;

import com.heroku.spacey.entity.Category;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryForProductMapper implements RowMapper<Category> {

    @Override
    public Category mapRow(ResultSet resultSet, int i) throws SQLException {
        Category category = new Category();
        category.setId(resultSet.getLong("category_id"));
        category.setName(resultSet.getString("category_name"));
        return category;
    }
}
