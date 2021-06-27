package com.heroku.spacey.mapper.auction.by_id;

import com.heroku.spacey.entity.*;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ProductForAuctionIdMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet resultSet, int i) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getLong("p_id"));
        product.setName(resultSet.getString("p_name"));
        product.setProductSex(resultSet.getString("p_sex"));
        product.setPhoto(resultSet.getString("p_photo"));
        product.setDescription(resultSet.getString("p_desc"));

        Category category = new Category();
        category.setId(resultSet.getLong("c_id"));
        category.setName(resultSet.getString("c_category"));

        Color color = new Color();
        color.setId(resultSet.getLong("clrs_id"));
        color.setName(resultSet.getString("clrs_color"));

        Set<Material> materials = new HashSet<>();
        do {
            Material material = new Material();
            material.setId(resultSet.getLong("m_id"));
            material.setName(resultSet.getString("material_name"));
            materials.add(material);
        }
        while (resultSet.next());

        product.setMaterials(materials);
        product.setProductCategory(category);
        product.setProductColor(color);

        return product;
    }
}
