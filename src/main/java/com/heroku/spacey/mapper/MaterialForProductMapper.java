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
        ArrayList<Material> materials = new ArrayList<>();
        do {
            var material = new Material();
            material.setId(resultSet.getLong("material_id"));
            material.setName(resultSet.getString("material_name"));
            materials.add(material);
        }
        while (resultSet.next());
        return materials;
    }
}
