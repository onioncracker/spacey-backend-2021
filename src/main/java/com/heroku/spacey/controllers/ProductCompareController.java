package com.heroku.spacey.controllers;


import com.heroku.spacey.dto.product.ProductCompareDto;
import com.heroku.spacey.dto.product.ProductItemDto;
import com.heroku.spacey.services.ProductCompareService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products/compared-products/")
public class ProductCompareController {

    private final ProductCompareService productCompareService;


    @PostMapping("")
    public HttpStatus addProductToCompare(@RequestBody ProductCompareDto compareDao) throws SQLException {
        productCompareService.addProductToCompare(compareDao);
        return HttpStatus.OK;
    }

    @GetMapping("/{id}")
    public List<ProductItemDto> getAllComparingProduct(@PathVariable(name = "id") Long userId) throws SQLException {
        return productCompareService.getAllComparingProduct(userId);

    }

    @DeleteMapping("")
    public HttpStatus deleteFromComparing(@RequestBody ProductCompareDto compareDto) throws SQLException {
        productCompareService.deleteProductCompare(compareDto);
        return HttpStatus.ACCEPTED;

    }
}
