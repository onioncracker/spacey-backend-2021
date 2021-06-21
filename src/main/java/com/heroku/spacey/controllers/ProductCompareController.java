package com.heroku.spacey.controllers;

import com.heroku.spacey.dto.product.ProductItemDto;
import com.heroku.spacey.services.ProductCompareService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products/compared-products")
public class ProductCompareController {

    private final ProductCompareService productCompareService;

    @Secured("ROLE_USER")
    @PostMapping("/{productId}")
    public HttpStatus addProductToCompare(@PathVariable Long productId) throws SQLException {
        productCompareService.addProductToCompare(productId);
        return HttpStatus.OK;
    }
    @Secured("ROLE_USER")
    @GetMapping("")
    public List<ProductItemDto> getAllComparingProduct() throws SQLException {
        return productCompareService.getAllComparingProduct();

    }
    @Secured("ROLE_USER")
    @DeleteMapping("/{productId}")
    public HttpStatus deleteFromComparing(@PathVariable Long productId) throws SQLException {
        productCompareService.deleteProductCompare(productId);
        return HttpStatus.ACCEPTED;

    }
}
