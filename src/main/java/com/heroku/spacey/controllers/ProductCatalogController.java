package com.heroku.spacey.controllers;

import com.heroku.spacey.dto.product.ProductItemDto;
import com.heroku.spacey.dto.product.ProductPageDto;
import com.heroku.spacey.services.ProductCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class ProductCatalogController {

    private final ProductCatalogService productCatalogServiceImpl;


    @GetMapping("/products/{id}")
    public ResponseEntity<ProductItemDto> getProduct(@PathVariable Long id) throws SQLException {
        return new ResponseEntity<>(productCatalogServiceImpl.getProduct(id), HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductPageDto>> getProducts(
            @RequestParam(required = false) Integer pageNum,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) String sex,
            @RequestParam(required = false) String price,
            @RequestParam(required = false) String categories,
            @RequestParam(required = false) String colors,
            @RequestParam(required = false) String order) throws SQLException {

        List<ProductPageDto> products = productCatalogServiceImpl.getAllProduct(
                pageNum,
                pageSize,
                sex,
                price,
                categories,
                colors,
                order
        );
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}
