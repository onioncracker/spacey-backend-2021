package com.heroku.spacey.mapper;

import com.heroku.spacey.entity.Material;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MaterialMapper implements RowMapper<Material> {
    @Override
    public Material mapRow(ResultSet resultSet, int i) throws SQLException {
        var material = new Material();
        material.setId(resultSet.getInt("id"));
        material.setName(resultSet.getString("name"));
        return material;
    }
}
