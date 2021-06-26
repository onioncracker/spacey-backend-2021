package com.heroku.spacey.mapper.auction;

import com.heroku.spacey.entity.Category;
import com.heroku.spacey.entity.Color;
import com.heroku.spacey.entity.Product;
import com.heroku.spacey.mapper.product.CategoryForProductMapper;
import com.heroku.spacey.mapper.product.ColorForProductMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

//TODO: finish product for auction mapper
public class ProductForAuctionMapper implements RowMapper<Product> {
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
        return product;
    }
}
