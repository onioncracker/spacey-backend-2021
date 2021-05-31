package com.heroku.spacey.repositories;

import com.heroku.spacey.dto.ProductCompareDto;
import com.heroku.spacey.dto.ProductDetailDto;

import java.sql.SQLException;
import java.util.List;

public interface ProductCompareDao {
    void addToCompare(ProductCompareDto compareDto) throws SQLException;
    void deleteCompare(ProductCompareDto compareDto) throws SQLException;
    List<ProductDetailDto> getAllProduct(int userId) throws SQLException;
    int getCountComparingProduct(int userId) throws SQLException;
    List<String> getMaterialByProductId (int productId) throws SQLException;
}
