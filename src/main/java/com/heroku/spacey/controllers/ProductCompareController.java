package com.heroku.spacey.controllers;

import com.heroku.spacey.dto.ProductCompareDto;
import com.heroku.spacey.dto.ProductDetailDto;
import com.heroku.spacey.services.ProductCompareService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    public HttpStatus addProductToCompare(@RequestBody ProductCompareDto compareDao) {
        try {
            productCompareService.addProductToCompare(compareDao);
            return HttpStatus.OK;
        } catch (IllegalArgumentException exception) {
            exception.printStackTrace();
            return HttpStatus.BAD_REQUEST;
        } catch (Exception exception) {
            exception.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @GetMapping("products/compared-products/{id}")
    public ResponseEntity<List<ProductDetailDto>> getAllComparingProduct(@PathVariable(name = "id") int userId) {
        try {
            return new ResponseEntity<>
                    (productCompareService.getAllComparingProduct(userId), HttpStatus.OK);

        } catch (SQLException exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping ("products/compared-products")
    public HttpStatus deleteFromComparing(@RequestBody ProductCompareDto compareDto) {
        try {
            productCompareService.deleteProductCompare(compareDto);
            return HttpStatus.ACCEPTED;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
