package com.heroku.spacey.controllers;

import com.heroku.spacey.dto.product.AddProductDto;
import com.heroku.spacey.services.CategoryService;
import com.heroku.spacey.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {
    final ProductService productService;
    final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity getProductById(@PathVariable int id) {
        try {
            return ResponseEntity.ok(productService.getProductDetailById(id));
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.toString());
        }
    }

    @PostMapping("/add")
    public ResponseEntity addProduct(@RequestBody AddProductDto addProductDto) {
        try {
            productService.addProduct(addProductDto);
            return ResponseEntity.ok("text");
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.toString());
        }
    }
}
