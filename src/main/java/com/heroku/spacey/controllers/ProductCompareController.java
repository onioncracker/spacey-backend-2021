package com.heroku.spacey.controllers;


import com.heroku.spacey.dto.product.ProductCompareDto;
import com.heroku.spacey.dto.product.ProductItemDto;
import com.heroku.spacey.services.ProductCompareService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductCompareController {

    private final ProductCompareService productCompareService;


    @PostMapping("products/compared-products")
    public HttpStatus addProductToCompare(@RequestBody ProductCompareDto compareDao) throws SQLException {
        productCompareService.addProductToCompare(compareDao);
        return HttpStatus.OK;
    }

    @GetMapping("products/compared-products/{id}")
    public ResponseEntity<List<ProductItemDto>> getAllComparingProduct(@PathVariable(name = "id") int userId) throws SQLException {
        return new ResponseEntity<>(productCompareService.getAllComparingProduct(userId), HttpStatus.OK);

    }

    @DeleteMapping("products/compared-products")
    public HttpStatus deleteFromComparing(@RequestBody ProductCompareDto compareDto) throws SQLException {
        productCompareService.deleteProductCompare(compareDto);
        return HttpStatus.ACCEPTED;

    }
}
