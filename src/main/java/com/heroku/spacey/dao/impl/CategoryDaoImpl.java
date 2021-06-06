package com.heroku.spacey.dao.impl;

import com.heroku.spacey.dao.CategoryDao;
import com.heroku.spacey.entity.Category;
import com.heroku.spacey.mapper.CategoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Slf4j
@Repository
@PropertySource("classpath:sql/category_queries.properties")
public class CategoryDaoImpl implements CategoryDao {
    private final CategoryMapper mapper = new CategoryMapper();
    private final JdbcTemplate jdbcTemplate;

    @Value("${category_get_by_id}")
    private String getCategoryById;
    @Value("${insert_category}")
    private String editCategory;
    @Value("${update_category}")
    private String updateCategory;
    @Value("${delete_category}")
    private String deleteCategory;

    public CategoryDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Category getById(int id) {
        List<Category> categories = Objects.requireNonNull(jdbcTemplate).query(getCategoryById, mapper, id);
        if (categories.isEmpty()) {
            return null;
        }
        return categories.get(0);
    }

    @Override
    public int insert(Category category) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(editCategory, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, category.getName());
            return ps;
        }, holder);
        return (int) Objects.requireNonNull(holder.getKeys()).get("categoryId");
    }

    @Override
    public void update(Category category) {
        Object[] params = new Object[]{category.getName(), category.getId()};
        Objects.requireNonNull(jdbcTemplate).update(updateCategory, params);
    }

    @Override
    public void delete(int id) {
        Objects.requireNonNull(jdbcTemplate).update(deleteCategory, id);
    }
}
