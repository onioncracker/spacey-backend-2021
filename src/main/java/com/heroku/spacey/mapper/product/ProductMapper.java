package com.heroku.spacey.mapper.product;

import com.heroku.spacey.entity.*;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ProductMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet resultSet, int i) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getLong("productid"));
        product.setCategoryId(resultSet.getLong("categoryid"));
        product.setColorId(resultSet.getLong("colorid"));
        product.setAmount(resultSet.getLong("amount"));
        product.setName(resultSet.getString("productname"));
        product.setCreatedDate(resultSet.getDate("createddate"));
        product.setProductSex(resultSet.getString("productsex"));
        product.setPrice(resultSet.getDouble("price"));
        product.setPhoto(resultSet.getString("photo"));
        product.setDescription(resultSet.getString("description"));
        product.setDiscount(resultSet.getDouble("discount"));
        product.setIsAvailable(resultSet.getBoolean("isavailable"));
        product.setIsOnAuction(resultSet.getBoolean("isonauction"));

        CategoryForProductMapper categoryMapper = new CategoryForProductMapper();
        ColorForProductMapper colorMapper = new ColorForProductMapper();
        Category category = categoryMapper.mapRow(resultSet, i);
        Color color = colorMapper.mapRow(resultSet, i);

        Set<Material> materials = new HashSet<>();
        Set<Size> sizes = new HashSet<>();
        do {
            Size size = new Size();
            Material material = new Material();
            size.setId(resultSet.getLong("size_id"));
            material.setId(resultSet.getLong("material_id"));
            size.setName(resultSet.getString("size_name"));
            material.setName(resultSet.getString("material_name"));
            size.setQuantity(resultSet.getLong("size_quantity"));
            sizes.add(size);
            materials.add(material);
        }
        while (resultSet.next());

        product.setMaterials(materials);
        product.setSizes(sizes);
        product.setProductCategory(category);
        product.setProductColor(color);
        return product;
    }
}
