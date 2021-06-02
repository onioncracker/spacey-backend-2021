package com.heroku.spacey.dao.category;

import com.heroku.spacey.dao.common.BaseDao;
import com.heroku.spacey.entity.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

@Slf4j
@Repository
@PropertySource("classpath:sql/category_queries.properties")
public class CategoryDaoImpl extends BaseDao implements CategoryDao {
    private final CategoryMapper mapper = new CategoryMapper();

    @Value("${category_get_by_id}")
    private String getCategoryById;
    @Value("${insert_category}")
    private String editCategory;
    @Value("${update_category}")
    private String updateCategory;
    @Value("${delete_category}")
    private String deleteCategory;

    public CategoryDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Category getById(int id) {
        var categories = Objects.requireNonNull(getJdbcTemplate()).query(getCategoryById, mapper, id);
        if (categories.isEmpty()) {
            return null;
        }
        return categories.get(0);
    }

    @Override
    public int insert(Category category) {
        try (PreparedStatement statement = Objects.requireNonNull(getDataSource())
                .getConnection().prepareStatement(editCategory, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, category.getName());
            return add(statement);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return -1;
    }

    @Override
    public void update(Category category) {
        var params = new Object[]{category.getName(), category.getId()};
        Objects.requireNonNull(getJdbcTemplate()).update(updateCategory, params);
    }

    @Override
    public void delete(int id) {
        Objects.requireNonNull(getJdbcTemplate()).update(deleteCategory, id);
    }
}
