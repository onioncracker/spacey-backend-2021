package com.heroku.spacey.controllers;

import com.heroku.spacey.dto.product.ProductItemDto;
import com.heroku.spacey.dto.product.ProductPageDto;
import com.heroku.spacey.services.ProductCatalogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@PropertySource("classpath:const.properties")
@RequestMapping("/api/v1/products")
public class ProductCatalogController {

    private final ProductCatalogService productCatalogServiceImpl;


    @GetMapping("/{id}")
    public ProductItemDto getProduct(@PathVariable Long id) throws SQLException {
        return productCatalogServiceImpl.getProduct(id);
    }

    @GetMapping("")
    public List<ProductPageDto> getProducts(
            @RequestParam(defaultValue = "${default_page_num}") Integer pageNum,
            @RequestParam(defaultValue = "${default_page_size}") Integer pageSize,
            @RequestParam(required = false) String sex,
            @RequestParam(required = false) String price,
            @RequestParam(required = false) String categories,
            @RequestParam(required = false) String colors,
            @RequestParam(required = false) String order) throws SQLException {

        return productCatalogServiceImpl.getAllProduct(
                null,
                pageNum,
                pageSize,
                sex,
                price,
                categories,
                colors,
                order
        );
    }


    @GetMapping("/search/{prompt}")
    public List<ProductPageDto> getProductsSearch(
            @PathVariable String prompt,
            @RequestParam(defaultValue = "${default_page_num}") Integer pageNum,
            @RequestParam(defaultValue = "${default_page_size}") Integer pageSize,
            @RequestParam(required = false) String sex,
            @RequestParam(required = false) String price,
            @RequestParam(required = false) String categories,
            @RequestParam(required = false) String colors,
            @RequestParam(required = false) String order) throws SQLException {

        return productCatalogServiceImpl.getAllProduct(
                prompt,
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
