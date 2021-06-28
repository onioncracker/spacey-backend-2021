package com.heroku.spacey.services.impl;

import com.amazonaws.services.apigateway.model.BadRequestException;
import com.heroku.spacey.dao.ProductCatalogDao;
import com.heroku.spacey.dto.product.ProductItemDto;
import com.heroku.spacey.dto.product.ProductPageDto;
import com.heroku.spacey.services.ProductCatalogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductCatalogServiceImpl implements ProductCatalogService {

    private final ProductCatalogDao catalog;

    public ProductItemDto getProduct(Long id) throws SQLException {
        return catalog.getProductById(id);
    }


    public List<ProductPageDto> getAllProduct(String prompt,
                                              Integer pageNum,
                                              Integer pageSize,
                                              String sex,
                                              String price,
                                              String categories,
                                              String colors,
                                              String order) throws SQLException {

        String[] categoriesParams = null;
        Integer[] priceParams = null;
        String[] colorsParams = null;

        if (StringUtils.hasText(categories)) {
            categoriesParams = categories.split(",");
        }

        if (StringUtils.hasText(price)) {
            priceParams = parseStringToIntArr(price);
            validatePrice(priceParams);
        }

        if (StringUtils.hasText(colors)) {
            colorsParams = colors.split(",");
        }

        return catalog.getAllProduct(
                prompt,
                categoriesParams,
                priceParams,
                sex,
                colorsParams,
                pageNum,
                pageSize,
                order);

    }


    private void validatePrice(Integer[] price) {
        if (price.length != 2) {
            throw new BadRequestException("Invalid price param, more or less arguments");
        }

        if (price[0] > price[1]) {
            throw new BadRequestException("Minimal price gather than maximal price");
        }

    }

    private Integer[] parseStringToIntArr(String params) {
        return Arrays.stream(params.split("-"))
                .map(Integer::parseInt)
                .toArray(Integer[]::new);
    }
}
