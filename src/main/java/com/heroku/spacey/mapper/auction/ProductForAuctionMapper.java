package com.heroku.spacey.mapper.auction;

import com.heroku.spacey.entity.*;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ProductForAuctionMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet resultSet, int i) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getLong("productid"));
        product.setName(resultSet.getString("productname"));
        product.setPhoto(resultSet.getString("photo"));
        product.setDescription(resultSet.getString("description"));

        Color color = new Color();
        color.setName(resultSet.getString("color"));
        Category category = new Category();
        category.setName(resultSet.getString("namecategory"));

        Set<Material> materials = new HashSet<>();
        do {
            Material material = new Material();
            material.setName(resultSet.getString("namematerial"));
            materials.add(material);
        }
        while (resultSet.next());

        product.setMaterials(materials);
        product.setProductCategory(category);
        product.setProductColor(color);
        return product;
    }
}
