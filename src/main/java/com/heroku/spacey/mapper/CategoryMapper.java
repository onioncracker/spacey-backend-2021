package com.heroku.spacey.mapper;

import com.heroku.spacey.entity.Category;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryMapper implements RowMapper<Category> {
    @Override
    public Category mapRow(ResultSet resultSet, int i) throws SQLException {
        var category = new Category();
        category.setId(resultSet.getInt("categoryid"));
        category.setName(resultSet.getString("namecategory"));
        return category;
    }
}
