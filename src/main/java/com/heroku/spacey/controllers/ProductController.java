package com.heroku.spacey.controllers;

import com.heroku.spacey.contracts.ProductService;
import com.heroku.spacey.dto.category.CategoryDto;
import com.heroku.spacey.dto.product.AddProductDto;
import com.heroku.spacey.dto.product.ProductDto;
import com.heroku.spacey.dto.product.UpdateProductDto;
import com.heroku.spacey.services.ProductServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {
    private final ProductService productService;


    public ProductController(ProductServiceImpl productServiceImpl) {
        this.productService = productServiceImpl;
    }

    @GetMapping("/api/product/{id}")
    public ResponseEntity<ProductDto> getCategory(@PathVariable int id) {
        try {
            var product = productService.getById(id);
            if (product == null) {
                return new ResponseEntity("product not found by id", HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/api/product/add")
    public ResponseEntity<String> addProduct(@RequestBody AddProductDto addProductDto) {
        try {
            productService.addProduct(addProductDto);
            return new ResponseEntity("added product successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/api/product/edit/{id}")
    public ResponseEntity<String> editProduct(@RequestBody UpdateProductDto updateProductDto) {
        try {
            productService.updateProduct(updateProductDto);
            return ResponseEntity.ok("successfully");
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.toString());
        }
    }

    @PutMapping("/api/product/remove/{id}")
    public ResponseEntity<String> removeProduct(@PathVariable int id) {
        try {
            productService.removeProduct(id);
            return ResponseEntity.ok("successfully");
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/api/product/cancel/{id}")
    public ResponseEntity<String> cancelAddingProduct(@PathVariable int id) {
        try {
            productService.cancelProduct(id);
            return ResponseEntity.ok("successfully");
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.toString());
        }
    }
}
