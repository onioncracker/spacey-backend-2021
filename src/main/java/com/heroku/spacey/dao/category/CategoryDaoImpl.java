package com.heroku.spacey.dao.category;

import com.heroku.spacey.dao.general.BaseDao;
import com.heroku.spacey.entity.Category;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

@Repository
@PropertySource("classpath:/productCatalog.properties")
public class CategoryDaoImpl extends BaseDao implements CategoryDao {
    private final CategoryMapper mapper = new CategoryMapper();

    public CategoryDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Category getById(int id) {
        String sql = "SELECT * FROM categories WHERE id = ?";
        Object[] params = new Object[]{id};
        return Objects.requireNonNull(getJdbcTemplate()).queryForObject(sql, mapper, params);
    }

    @Override
    public int insert(Category category) {
        String sql = "INSERT INTO categories(name) VALUES (?)";
        try (PreparedStatement statement = getDataSource().getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, category.getName());
            return add(statement);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    @Override
    public void update(Category category) {
        String sql = "UPDATE categories SET name = ? WHERE id = ?";
        Object[] params = new Object[]{category.getName(), category.getId()};
        Objects.requireNonNull(getJdbcTemplate()).update(sql, params);
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM categories WHERE id=?";
        getJdbcTemplate().update(sql, id);
    }
}
