package com.heroku.spacey.services;

import com.heroku.spacey.dto.ProductDetailDto;
import com.heroku.spacey.dto.ProductPageDto;
import com.heroku.spacey.repositories.ProductCatalogDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductCatalogService {

    private final ProductCatalogDao catalog;

    public ProductDetailDto getProduct(int id) throws SQLException {
        return catalog.getProductById(id);
    }


    public List<ProductPageDto> getAllProduct(String page,
                                                  String price,
                                                  String categories,
                                                  String colors,
                                                  String size,
                                                  String order) throws SQLException {

        Map<String, String> filters = new HashMap<>();
        int pageSize = 10;
        int pageNum = 0;
        Integer[] pageParams = null;
        String[] categoriesParams = null;
        Integer[] priceParams = null;
        String[] sizesParams = null;
        String[] colorsParams = null;

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

        if (isParamValid(size)) {
            sizesParams = size.split(",");
        }

        if (isParamValid(page)) {
            pageParams = parseStringToIntArr(page);
            validatePages(pageParams);
            pageNum = pageParams[0];
            pageSize = pageParams[1];
        }
        return catalog.getAllProduct(
                categoriesParams,
                priceParams,
                colorsParams,
                sizesParams,
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

    private void validatePages(Integer[] page) {
        if (page.length != 2) {
            throw new IllegalArgumentException("Pages param format is not valid");
        }
    }

    private Integer[] parseStringToIntArr(String params) {
        return Arrays.stream(params.split("-"))
                .map(Integer::parseInt)
                .toArray(Integer[]::new);
    }
}
