package com.heroku.spacey.controllers;

import com.heroku.spacey.services.ProductService;
import com.heroku.spacey.dto.product.AddProductDto;
import com.heroku.spacey.dto.product.ProductDto;
import com.heroku.spacey.dto.product.UpdateProductDto;
import com.heroku.spacey.services.impl.ProductServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductServiceImpl productServiceImpl) {
        this.productService = productServiceImpl;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable int id) {
        try {
            ProductDto product = productService.getById(id);
            if (product == null) {
                return new ResponseEntity("product not found by id", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(product, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ProductDto> addProduct(@RequestBody AddProductDto addProductDto) {
        try {
            productService.addProduct(addProductDto);
            return new ResponseEntity("added product successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ProductDto> editProduct(@RequestBody UpdateProductDto updateProductDto, @PathVariable int id) {
        try {
            productService.updateProduct(updateProductDto, id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/remove/{id}")
    public ResponseEntity<ProductDto> removeProduct(@PathVariable int id) {
        try {
            productService.removeProduct(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<ProductDto> cancelAddingProduct(@PathVariable int id) {
        try {
            productService.cancelProduct(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
