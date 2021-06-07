package com.heroku.spacey.mapper;

import com.heroku.spacey.entity.*;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProductMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet resultSet, int i) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getLong("productid"));
        product.setCategoryId(resultSet.getLong("categoryid"));
        product.setName(resultSet.getString("productname"));
        product.setCreatedDate(resultSet.getDate("createddate"));
        product.setProductSex(resultSet.getString("productsex"));
        product.setPrice(resultSet.getBigDecimal("price"));
        product.setPhoto(resultSet.getString("photo"));
        product.setDescription(resultSet.getString("description"));
        product.setDiscount(resultSet.getDouble("discount"));
        product.setIsAvailable(resultSet.getBoolean("isavailable"));
        product.setIsOnAuction(resultSet.getBoolean("isonauction"));

        CategoryForProductMapper categoryMapper = new CategoryForProductMapper();
        MaterialForProductMapper materialMapper = new MaterialForProductMapper();
        ProductDetailsForProductMapper productDetailsMapper = new ProductDetailsForProductMapper();

        Category category = categoryMapper.mapRow(resultSet, i);
        ProductDetail productDetails = productDetailsMapper.mapRow(resultSet, i);
        List<Material> materials = materialMapper.mapRow(resultSet, i);

        product.setMaterials(materials);
        product.setProductCategory(category);
        product.setProductDetail(productDetails);
        return product;
    }
}
