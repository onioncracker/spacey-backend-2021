package com.heroku.spacey.controllers;

import lombok.RequiredArgsConstructor;
import com.heroku.spacey.dto.order.CheckoutDto;
import com.heroku.spacey.services.CheckoutService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.annotation.Secured;

@RestController
@Secured("ROLE_USER")
@RequiredArgsConstructor
@RequestMapping("/api/checkout")
public class CheckoutController {
    private final CheckoutService checkoutService;

    @GetMapping("")
    public CheckoutDto getCheckoutByUserId() {
        return checkoutService.getCheckoutByUserId();
    }

    @GetMapping("/{cartId}")
    public CheckoutDto getCheckoutByCartId(@PathVariable Long cartId) {
        return checkoutService.getCheckoutByCartId(cartId);
    }
}
