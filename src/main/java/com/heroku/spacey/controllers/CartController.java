package com.heroku.spacey.controllers;

import com.heroku.spacey.dto.cart.EditCartDto;
import com.heroku.spacey.services.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @Secured("ROLE_USER")
    @PutMapping("/add")
    public ResponseEntity addProduct(@RequestBody EditCartDto editCartDto) {
        cartService.addProductToCart(editCartDto.getProductId(), editCartDto.getAmount());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete")
    public ResponseEntity deleteProduct(@RequestBody EditCartDto editCartDto) {
        return null;
    }

}
