package com.heroku.spacey.dao.impl;

import com.heroku.spacey.dao.MaterialDao;
import com.heroku.spacey.entity.Material;
import com.heroku.spacey.mapper.MaterialMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Objects;

@Slf4j
@Repository
@PropertySource("classpath:sql/material_queries.properties")
public class MaterialDaoImpl implements MaterialDao {
    private final MaterialMapper mapper = new MaterialMapper();
    private final JdbcTemplate jdbcTemplate;

    @Value("${material_get_by_id}")
    private String getMaterialById;
    @Value("${insert_material}")
    private String editMaterial;
    @Value("${update_material}")
    private String updateMaterial;
    @Value("${delete_material}")
    private String deleteMaterial;

    public MaterialDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Material getById(int id) {
        return Objects.requireNonNull(jdbcTemplate).queryForObject(getMaterialById, mapper, id);
    }

    @Override
    public int insert(Material material) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(editMaterial, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, material.getName());
            return ps;
        }, holder);
        return (int) Objects.requireNonNull(holder.getKeys()).get("materialId");
    }

    @Override
    public void update(Material material) {
        Object[] params = new Object[]{material.getName(), material.getId()};
        Objects.requireNonNull(jdbcTemplate).update(updateMaterial, params);
    }

    @Override
    public void delete(int id) {
        Objects.requireNonNull(jdbcTemplate).update(deleteMaterial, id);
    }
}
