package com.heroku.spacey.repositories;

import com.heroku.spacey.dto.ProductDetailDto;
import com.heroku.spacey.dto.ProductPageDto;

import java.sql.SQLException;
import java.util.List;

public interface ProductCatalogDao {
    ProductDetailDto getProductById (int id) throws SQLException;
    List<ProductPageDto> getAllProduct(String[] categories,
                                   Integer[] prices,
                                   String[] colors,
                                   String[] size,
                                   int pageNum,
                                   int pageSize,
                                   String order) throws SQLException;
}
