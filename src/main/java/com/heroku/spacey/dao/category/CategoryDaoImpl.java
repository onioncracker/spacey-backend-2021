package com.heroku.spacey.dao.category;

import com.heroku.spacey.dao.common.BaseDao;
import com.heroku.spacey.dao.common.IdMapper;
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
@PropertySource("classpath:sql/productCatalog.properties")
public class CategoryDaoImpl extends BaseDao implements CategoryDao {
    private final CategoryMapper mapper = new CategoryMapper();
    private final IdMapper idMapper = new IdMapper();

    @Value("${category_get_by_id}")
    private String getCategoryById;
    @Value("${insert_category}")
    private String insertCategory;
    @Value("${update_category}")
    private String updateCategory;
    @Value("${delete_category}")
    private String deleteCategory;

    public CategoryDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Category getById(int id) {
        //var sql = "SELECT * FROM categories WHERE id = ?";
        //SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", 1);
        var categories = Objects.requireNonNull(getJdbcTemplate()).query(getCategoryById, mapper, id);
        if (categories.isEmpty()) {
            return null;
        }
        return categories.get(0);
    }

    @Override
    public boolean isExist(int id) {
        var sql = "SELECT c.id FROM categories c WHERE c.id = ?";
        var category = Objects.requireNonNull(getJdbcTemplate()).query(sql, idMapper, id);
        return !category.isEmpty();
    }

    @Override
    public int insert(Category category) {
        var sql = "INSERT INTO categories(name) VALUES (?)";
        try (PreparedStatement statement = Objects.requireNonNull(getDataSource())
                .getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, category.getName());
            return add(statement);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return -1;
    }

    @Override
    public void update(Category category) {
        var sql = "UPDATE categories SET name = ? WHERE id = ?";
        var params = new Object[]{category.getName(), category.getId()};
        Objects.requireNonNull(getJdbcTemplate()).update(sql, params);
    }

    @Override
    public void delete(int id) {
        var sql = "DELETE FROM categories WHERE id=?";
        Objects.requireNonNull(getJdbcTemplate()).update(sql, id);
    }
}
