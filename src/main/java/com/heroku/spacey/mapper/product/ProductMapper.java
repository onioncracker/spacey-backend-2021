package com.heroku.spacey.mapper.product;

import com.heroku.spacey.entity.*;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        product.setPrice(resultSet.getBigDecimal("price"));
        product.setPhoto(resultSet.getString("photo"));
        product.setDescription(resultSet.getString("description"));
        product.setDiscount(resultSet.getDouble("discount"));
        product.setIsAvailable(resultSet.getBoolean("isavailable"));
        product.setIsOnAuction(resultSet.getBoolean("isonauction"));

        CategoryForProductMapper categoryMapper = new CategoryForProductMapper();
        MaterialForProductMapper materialMapper = new MaterialForProductMapper();
        ColorForProductMapper colorMapper = new ColorForProductMapper();
        SizeForProductMapper sizeMapper = new SizeForProductMapper();

        Category category = categoryMapper.mapRow(resultSet, i);
        Color color = colorMapper.mapRow(resultSet, i);

        ArrayList<Material> materials = new ArrayList<>();
        ArrayList<Size> sizes = new ArrayList<>();
        do {
            Size size = new Size();
            Material material = new Material();
            size.setId(resultSet.getLong("size_id"));
            material.setId(resultSet.getLong("material_id"));
            size.setName(resultSet.getString("size_name"));
            material.setName(resultSet.getString("material_name"));
            sizes.add(size);
            materials.add(material);
        }
        while (resultSet.next());

//        List<Material> materials = materialMapper.mapRow(resultSet, i);
        product.setMaterials(materials);

//        List<Size> sizes = sizeMapper.mapRow(resultSet, i);
        product.setSizes(sizes);

        product.setProductCategory(category);
        product.setProductColor(color);
        return product;
    }
}
