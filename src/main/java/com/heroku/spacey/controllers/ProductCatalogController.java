package com.heroku.spacey.controllers;

import com.heroku.spacey.dto.product.ProductItemDto;
import com.heroku.spacey.dto.product.ProductPageDto;
import com.heroku.spacey.services.ProductCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;


@RestController
@RequiredArgsConstructor
@PropertySource("classpath:const.properties")
public class ProductCatalogController {

    private final ProductCatalogService productCatalogServiceImpl;


    @GetMapping("/products/{id}")
    public ProductItemDto getProduct(@PathVariable Long id) throws SQLException {
        return productCatalogServiceImpl.getProduct(id);
    }

    @GetMapping("/products")
    public List<ProductPageDto> getProducts(
            @RequestParam(defaultValue = "${default_page_num}") Integer pageNum,
            @RequestParam(defaultValue = "${default_page_size}") Integer pageSize,
            @RequestParam(required = false) String sex,
            @RequestParam(required = false) String price,
            @RequestParam(required = false) String categories,
            @RequestParam(required = false) String colors,
            @RequestParam(required = false) String order) throws SQLException {

        return productCatalogServiceImpl.getAllProduct(
                pageNum,
                pageSize,
                sex,
                price,
                categories,
                colors,
                order
        );
    }

}
