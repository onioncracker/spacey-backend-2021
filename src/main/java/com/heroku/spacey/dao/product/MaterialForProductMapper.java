package com.heroku.spacey.dao.product;

import com.heroku.spacey.entity.Material;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialForProductMapper implements RowMapper<List<Material>> {

    @Override
    public List<Material> mapRow(ResultSet resultSet, int i) throws SQLException {
        Array array = resultSet.getArray("material_name");
        String[] materialNames = (String[]) array.getArray();
        int[] materialIds = (int[]) resultSet.getArray("material_id").getArray();

        var materials = new ArrayList<Material>();
        for (var j = 0; j < materialIds.length; j++) {
            var material = new Material();
            material.setId(materialIds[j]);
            material.setName(materialNames[j]);
            materials.add(material);
        }
        return materials;
    }
}
