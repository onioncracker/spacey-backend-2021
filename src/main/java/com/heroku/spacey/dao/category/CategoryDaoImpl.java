package com.heroku.spacey.dao.category;

import com.heroku.spacey.dao.general.BaseDao;
import com.heroku.spacey.dao.general.IdMapper;
import com.heroku.spacey.entity.Category;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

@Repository
public class CategoryDaoImpl extends BaseDao implements CategoryDao {
    private final CategoryMapper mapper = new CategoryMapper();
    private final IdMapper idMapper = new IdMapper();

    public CategoryDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Category getById(int id) {
        String sql = "SELECT * FROM categories WHERE id = ?";
        Object[] params = new Object[]{id};
        var categories = Objects.requireNonNull(getJdbcTemplate()).query(sql, mapper, params);
        if (categories.isEmpty()) {
            return null;
        }
        return categories.get(0);
    }

    @Override
    public boolean isExist(int id) {
        String sql = "SELECT c.id \n" +
                "FROM categories c\n" +
                "WHERE c.id = ?";
        Object[] params = new Object[]{id};
        var category = getJdbcTemplate().query(sql, idMapper, params);
        return !category.isEmpty();
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
        Objects.requireNonNull(getJdbcTemplate()).update(sql, id);
    }
}
