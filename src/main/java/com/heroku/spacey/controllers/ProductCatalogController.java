package com.heroku.spacey.controllers;

import com.heroku.spacey.dto.ProductDetailDto;
import com.heroku.spacey.dto.ProductPageDto;
import com.heroku.spacey.services.ProductCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class ProductCatalogController {

    private final ProductCatalogService productCatalogService;


    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDetailDto> getProduct(@PathVariable int id) {
        try {
            return new ResponseEntity<>(productCatalogService.getProduct(id), HttpStatus.OK);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductPageDto>> getProducts(
            @RequestParam(required = false) String page,
            @RequestParam(required = false) String price,
            @RequestParam(required = false) String categories,
            @RequestParam(required = false) String colors,
            @RequestParam(required = false) String size,
            @RequestParam(required = false) String order) {
        try {
            return new ResponseEntity<>(productCatalogService.getAllProduct(page, price, categories, colors, size, order), HttpStatus.OK);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (IllegalArgumentException exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
