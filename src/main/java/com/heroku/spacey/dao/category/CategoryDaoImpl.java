package com.heroku.spacey.dao.category;

import com.heroku.spacey.entity.Category;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class CategoryDaoImpl extends JdbcDaoSupport implements CategoryDao{
    private CategoryMapper mapper = new CategoryMapper();

    public CategoryDaoImpl(DataSource dataSource) {
        this.setDataSource(dataSource);
    }


    @Override
    public Category getById(int id) {
        return null;
    }

    @Override
    public int insert(Category category) {
        String sql = "INSERT INTO categories(name) VALUES (?)";
        try {
            PreparedStatement statement = getDataSource().getConnection().prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, category.getName());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Pizdec!");
            }
            try (ResultSet resultSet = statement.getGeneratedKeys()){
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                } else {
                    throw new SQLException("Double pizdec!");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
//        Object[] params = new Object[]{category.getName()};
//        return getJdbcTemplate().update(sql, params);
    }

    @Override
    public Category update(Category category) {
        return null;
    }

    @Override
    public Category delete(int id) {
        return null;
    }
}
