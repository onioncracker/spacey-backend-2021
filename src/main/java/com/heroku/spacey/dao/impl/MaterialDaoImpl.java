package com.heroku.spacey.dao.impl;

import com.heroku.spacey.dao.common.BaseDao;
import com.heroku.spacey.dao.MaterialDao;
import com.heroku.spacey.entity.Material;
import com.heroku.spacey.mapper.MaterialMapper;
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
@PropertySource("classpath:sql/material_queries.properties")
public class MaterialDaoImpl extends BaseDao implements MaterialDao {
    private final MaterialMapper mapper = new MaterialMapper();

    @Value("${material_get_by_id}")
    private String getMaterialById;
    @Value("${insert_material}")
    private String editMaterial;
    @Value("${update_material}")
    private String updateMaterial;
    @Value("${delete_material}")
    private String deleteMaterial;

    public MaterialDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Material getById(int id) {
        return Objects.requireNonNull(getJdbcTemplate()).queryForObject(getMaterialById, mapper, id);
    }

    @Override
    public int insert(Material material) {
        try (PreparedStatement statement = Objects.requireNonNull(getDataSource())
                .getConnection().prepareStatement(editMaterial, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, material.getName());
            return add(statement);
        } catch (SQLException e) {
            log.info(e.getMessage());
        }
        return -1;
    }

    @Override
    public void update(Material material) {
        var params = new Object[]{material.getName(), material.getId()};
        Objects.requireNonNull(getJdbcTemplate()).update(updateMaterial, params);
    }

    @Override
    public void delete(int id) {
        Objects.requireNonNull(getJdbcTemplate()).update(deleteMaterial, id);
    }
}
