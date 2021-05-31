package com.heroku.spacey.controllers;

import com.heroku.spacey.contracts.ProductService;
import com.heroku.spacey.dto.product.AddProductDto;
import com.heroku.spacey.dto.product.UpdateProductDto;
import com.heroku.spacey.services.ProductServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductServiceImpl productServiceImpl) {
        this.productService = productServiceImpl;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestBody AddProductDto addProductDto) {
        try {
            productService.addProduct(addProductDto);
            return ResponseEntity.ok("successfully");
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.toString());
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<String> editProduct(@RequestBody UpdateProductDto updateProductDto) {
        try {
            productService.updateProduct(updateProductDto);
            return ResponseEntity.ok("successfully");
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.toString());
        }
    }

    @PutMapping("/remove/{id}")
    public ResponseEntity<String> removeProduct(@PathVariable int id) {
        try {
            productService.removeProduct(id);
            return ResponseEntity.ok("successfully");
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.toString());
        }
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<String> cancelAddingProduct(@PathVariable int id) {
        try {
            productService.cancelProduct(id);
            return ResponseEntity.ok("successfully");
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(e.toString());
        }
    }
}
