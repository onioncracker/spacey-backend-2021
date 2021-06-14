package com.heroku.spacey.controllers;

import com.heroku.spacey.dto.cart.EditCartDto;
import com.heroku.spacey.dto.product.ProductForCartDto;
import com.heroku.spacey.services.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @Secured("ROLE_USER")
    @PutMapping("/add-product")
    public HttpStatus addProduct(@RequestBody EditCartDto editCartDto) {
        cartService.addProductToCart(editCartDto.getProductId(), editCartDto.getAmount());
        return HttpStatus.OK;
    }

    @Secured("ROLE_USER")
    @DeleteMapping("/delete-product")
    public HttpStatus deleteProduct(@RequestBody EditCartDto editCartDto) {
        cartService.deleteProductFromCart(editCartDto.getProductId(), editCartDto.getAmount());
        return HttpStatus.OK;
    }

    @Secured("ROLE_USER")
    @DeleteMapping("/clear")
    public HttpStatus clearCart() {
        cartService.cleanCart();
        return HttpStatus.OK;
    }

    @Secured("ROLE_USER")
    @GetMapping("/get-all-cart")
    public List<ProductForCartDto> getAllProductsInCart() {
        return cartService.getAllProductsInCart();
    }

}
