package com.heroku.spacey.controllers;

import com.heroku.spacey.services.ProductService;
import com.heroku.spacey.dto.product.AddProductDto;
import com.heroku.spacey.dto.product.ProductDto;
import com.heroku.spacey.dto.product.UpdateProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable int id) {
        ProductDto product = productService.getById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestBody AddProductDto addProductDto) {
        productService.addProduct(addProductDto);
        return new ResponseEntity<>("Add product successfully", HttpStatus.CREATED);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ProductDto> editProduct(@RequestBody UpdateProductDto updateProductDto,
                                                  @PathVariable int id) {
        ProductDto product = productService.getById(id);
        productService.updateProduct(updateProductDto, product.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/remove/{id}")
    public ResponseEntity<ProductDto> removeProduct(@PathVariable int id) {
        ProductDto product = productService.getById(id);
        productService.removeProduct(product.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<ProductDto> cancelAddingProduct(@PathVariable int id) {
        ProductDto product = productService.getById(id);
        productService.cancelProduct(product.getId());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
