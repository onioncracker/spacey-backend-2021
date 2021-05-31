package com.heroku.spacey.dao.product;

import com.heroku.spacey.entity.*;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet resultSet, int i) throws SQLException {
        var product = new Product();
        product.setId(resultSet.getInt("id"));
        product.setCategoryId(resultSet.getInt("categoryid"));
        product.setName(resultSet.getString("name"));
        product.setProductSex(resultSet.getString("createddate"));
        product.setProductSex(resultSet.getString("productsex"));
        product.setPrice(resultSet.getBigDecimal("price"));
        product.setPhoto(resultSet.getString("photo"));
        product.setDescription(resultSet.getString("description"));
        product.setDiscount(resultSet.getDouble("discount"));
        product.setIsAvailable(resultSet.getBoolean("isavailable"));

        var categoryMapper = new CategoryForProductMapper();
        var materialMapper = new MaterialForProductMapper();
        var productDetailsMapper = new ProductDetailsForProductMapper();

        var category = categoryMapper.mapRow(resultSet, i);
        var materials = materialMapper.mapRow(resultSet, i);

        var productDetails = productDetailsMapper.mapRow(resultSet, i);

        product.setMaterials(materials);
        product.setCategory(category);
        product.setProductDetails(productDetails);
        return product;
    }
}
