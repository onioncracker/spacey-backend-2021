package com.heroku.spacey.mapper;

import com.heroku.spacey.entity.Material;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialForProductMapper implements RowMapper<List<Material>> {

    @Override
    public List<Material> mapRow(ResultSet resultSet, int i) throws SQLException {
        var materials = new ArrayList<Material>();
        do {
            var material = new Material();
            material.setId(resultSet.getInt("material_id"));
            material.setName(resultSet.getString("material_name"));
            materials.add(material);
        }
        while (resultSet.next());
        return materials;
    }
}
