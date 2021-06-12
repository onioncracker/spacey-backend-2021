package com.heroku.spacey.mapper.product;

import com.heroku.spacey.entity.Category;
import com.heroku.spacey.entity.Color;
import com.heroku.spacey.entity.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductsMapper implements RowMapper<List<Product>> {
    @Override
    public List<Product> mapRow(ResultSet resultSet, int i) throws SQLException {
//        List<Product> products = new ArrayList<>();
//        Long productIdPrev = resultSet.getLong("productid");
//        do {
//            Long productid = resultSet.getLong("productid");
//            if (productid ) {
//                Product product = new Product();
//                product.setId(productid);
//                product.setCategoryId(resultSet.getLong("categoryid"));
//                product.setColorId(resultSet.getLong("colorid"));
//                product.setAmount(resultSet.getLong("amount"));
//                product.setName(resultSet.getString("productname"));
//                product.setCreatedDate(resultSet.getDate("createddate"));
//                product.setProductSex(resultSet.getString("productsex"));
//                product.setPrice(resultSet.getBigDecimal("price"));
//                product.setPhoto(resultSet.getString("photo"));
//                product.setDescription(resultSet.getString("description"));
//                product.setDiscount(resultSet.getDouble("discount"));
//                product.setIsAvailable(resultSet.getBoolean("isavailable"));
//                product.setIsOnAuction(resultSet.getBoolean("isonauction"));
//                CategoryForProductMapper categoryMapper = new CategoryForProductMapper();
//                ColorForProductMapper colorMapper = new ColorForProductMapper();
//                Category category = categoryMapper.mapRow(resultSet, i);
//                Color color = colorMapper.mapRow(resultSet, i);
//                products.add(product);
//            }
//        } while (resultSet.next());
//        return products;
        return null;
    }
}
