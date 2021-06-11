package com.heroku.spacey.services.impl;


import com.heroku.spacey.dao.ProductCatalogDao;
import com.heroku.spacey.dto.product.ProductItemDto;
import com.heroku.spacey.dto.product.ProductPageDto;
import com.heroku.spacey.services.ProductCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductCatalogServiceImpl implements ProductCatalogService {

    private final ProductCatalogDao catalog;
    private final Integer PAGE_NUM = 0;
    private final Integer PAGE_SIZE = 8;

    public ProductItemDto getProduct(Long id) throws SQLException {
        return catalog.getProductById(id);
    }


    public List<ProductPageDto> getAllProduct(Integer pageNum,
                                              Integer pageSize,
                                              String sex,
                                              String price,
                                              String categories,
                                              String colors,
                                              String order) throws SQLException {

        Map<String, String> filters = new HashMap<>();
        String[] categoriesParams = null;
        Integer[] priceParams = null;
        String[] colorsParams = null;

        if(pageNum == null){
            pageNum = PAGE_NUM;
        }
        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }

        if (isParamValid(categories)) {
            categoriesParams = categories.split(",");
        }

        if (isParamValid(price)) {
            priceParams = parseStringToIntArr(price);
            validatePrice(priceParams);

        }

        if (isParamValid(colors)) {
            colorsParams = colors.split(",");
        }

        return catalog.getAllProduct(
                categoriesParams,
                priceParams,
                sex,
                colorsParams,
                pageNum,
                pageSize,
                order);

    }

    private boolean isParamValid(String var) {
        if (var == null) {
            return false;
        }

        if (var.isEmpty()) {
            throw new IllegalArgumentException("Invalid query params");
        }
        return true;
    }

    private void validatePrice(Integer[] price) {
        if (price.length != 2) {
            throw new IllegalArgumentException("Invalid price param, more or less arguments");
        }

        if (price[0] > price[1]) {
            throw new IllegalArgumentException("Minimal price gather than maximal price");
        }

    }


    private Integer[] parseStringToIntArr(String params) {
        return Arrays.stream(params.split("-"))
                .map(Integer::parseInt)
                .toArray(Integer[]::new);
    }
}
