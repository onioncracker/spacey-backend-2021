package com.heroku.spacey.dao.material;

import com.heroku.spacey.dao.common.BaseDao;
import com.heroku.spacey.dao.common.IdMapper;
import com.heroku.spacey.entity.Material;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

@Slf4j
@Repository
public class MaterialDaoImpl extends BaseDao implements MaterialDao {
    private final MaterialMapper mapper = new MaterialMapper();
    private final IdMapper idMapper = new IdMapper();

    public MaterialDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Material getById(int id) {
        var sql = "SELECT * FROM materials WHERE id = ?";
        return Objects.requireNonNull(getJdbcTemplate()).queryForObject(sql, mapper, id);
    }

    @Override
    public boolean isExist(int id) {
        var sql = "SELECT m.id FROM materials m WHERE m.id = ?";
        var materials = Objects.requireNonNull(getJdbcTemplate()).query(sql, idMapper, id);
        return !materials.isEmpty();
    }

    @Override
    public int insert(Material material) {
        var sql = "INSERT INTO materials(name) VALUES (?)";
        try (PreparedStatement statement = Objects.requireNonNull(getDataSource())
                .getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, material.getName());
            return add(statement);
        } catch (SQLException e) {
            log.info(e.getMessage());
        }
        return -1;
    }

    @Override
    public void update(Material material) {
        var sql = "UPDATE materials SET name = ? WHERE id = ?";
        var params = new Object[]{material.getName(), material.getId()};
        Objects.requireNonNull(getJdbcTemplate()).update(sql, params);
    }

    @Override
    public void delete(int id) {
        var sql = "DELETE FROM materials WHERE id=?";
        Objects.requireNonNull(getJdbcTemplate()).update(sql, id);
    }
}
