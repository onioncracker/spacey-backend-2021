package com.heroku.spacey.controllers;

import com.heroku.spacey.dto.cart.EditCartDto;
import com.heroku.spacey.dto.cart.ProductForCartDto;
import com.heroku.spacey.services.CartService;
import com.heroku.spacey.services.ProductService;
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
    private final ProductService productService;

    public CartController(CartService cartService,
                          ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @Secured("ROLE_USER")
    @PostMapping("/add-product")
    public HttpStatus addProduct(@RequestBody EditCartDto editCartDto) {
        cartService.addProductToCart(editCartDto.getProductId(), editCartDto.getSizeId(), editCartDto.getAmount());
        return HttpStatus.OK;
    }

    @Secured("ROLE_USER")
    @DeleteMapping("/delete-product")
    public HttpStatus deleteProduct(@RequestBody EditCartDto editCartDto) {
        cartService.deleteProductFromCart(editCartDto.getProductId(), editCartDto.getSizeId(), editCartDto.getAmount());
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

    @PostMapping("/check-product-for-cart")
    public HttpStatus checkProductForCart(@RequestBody EditCartDto editCartDto) {
        boolean check = productService.checkAmount(editCartDto.getProductId(),
            editCartDto.getSizeId(), editCartDto.getAmount());
        if (check) {
            return HttpStatus.OK;
        }
        return HttpStatus.FORBIDDEN;
    }

    @PostMapping("/get-unchecked-cart")
    public List<ProductForCartDto> getUnauthorizedCart(@RequestBody List<EditCartDto> cart) {
        return cartService.getUnauthorizedCart(cart);
    }

}
