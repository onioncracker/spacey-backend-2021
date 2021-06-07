package com.heroku.spacey.mapper;

import com.heroku.spacey.entity.Material;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MaterialMapper implements RowMapper<Material> {
    @Override
    public Material mapRow(ResultSet resultSet, int i) throws SQLException {
        Material material = new Material();
        material.setId(resultSet.getLong("materialid"));
        material.setName(resultSet.getString("namematerial"));
        return material;
    }
}
