package com.heroku.spacey.controllers;

import com.heroku.spacey.services.AwsImageService;
import com.heroku.spacey.services.ProductService;
import com.heroku.spacey.dto.product.AddProductDto;
import com.heroku.spacey.dto.product.ProductDto;
import com.heroku.spacey.dto.product.UpdateProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;
    private final AwsImageService awsImageService;

    @GetMapping("/all")
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable Long id) {
        return productService.getById(id);
    }

    @PostMapping("/add")
    @Secured("ROLE_PRODUCT_MANAGER")
    @ResponseStatus(HttpStatus.CREATED)
    public Long addProduct(@RequestBody AddProductDto addProductDto) {
        return productService.addProduct(addProductDto);
    }

    @PutMapping("/edit/{id}")
    @Secured("ROLE_PRODUCT_MANAGER")
    public HttpStatus editProduct(@RequestBody UpdateProductDto updateProductDto,
                                  @PathVariable Long id) {
        ProductDto product = productService.getById(id);
        productService.updateProduct(updateProductDto, product.getId());
        return HttpStatus.OK;
    }

    @PutMapping("/remove/{id}")
    @Secured("ROLE_PRODUCT_MANAGER")
    public HttpStatus removeProduct(@PathVariable Long id) {
        ProductDto product = productService.getById(id);
        productService.removeProduct(product.getId());
        return HttpStatus.OK;
    }

    @DeleteMapping("/cancel/{id}")
    @Secured("ROLE_PRODUCT_MANAGER")
    public HttpStatus cancelAddingProduct(@PathVariable Long id) {
        ProductDto product = productService.getById(id);
        awsImageService.delete(product.getPhoto());
        productService.cancelProduct(product.getId());
        return HttpStatus.ACCEPTED;
    }
}
