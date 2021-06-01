package com.heroku.spacey.dao.material;

import com.heroku.spacey.dao.general.BaseDao;
import com.heroku.spacey.dao.general.IdMapper;
import com.heroku.spacey.entity.Material;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

@Repository
public class MaterialDaoImpl extends BaseDao implements MaterialDao {
    private final MaterialMapper mapper = new MaterialMapper();
    private final IdMapper idMapper = new IdMapper();

    public MaterialDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Material getById(int id) {
        String sql = "SELECT * FROM materials WHERE id = ?";
        Object[] params = new Object[]{id};
        return Objects.requireNonNull(getJdbcTemplate()).queryForObject(sql, mapper, params);
    }

    @Override
    public boolean isExist(int id) {
        String sql = "SELECT m.id \n" +
                "FROM materials m\n" +
                "WHERE m.id = ?";
        Object[] params = new Object[]{id};
        var materials = getJdbcTemplate().query(sql, idMapper, params);
        return !materials.isEmpty();
    }

    @Override
    public int insert(Material material) {
        String sql = "INSERT INTO materials(name) VALUES (?)";
        try (PreparedStatement statement = getDataSource()
                .getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, material.getName());
            return add(statement);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    @Override
    public void update(Material material) {
        String sql = "UPDATE materials SET name = ? WHERE id = ?";
        Object[] params = new Object[]{material.getName(), material.getId()};
        Objects.requireNonNull(getJdbcTemplate()).update(sql, params);
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM materials WHERE id=?";
        Objects.requireNonNull(getJdbcTemplate()).update(sql, id);
    }
}
