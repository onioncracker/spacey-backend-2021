package com.heroku.spacey.controllers;

import com.heroku.spacey.dto.order.CheckoutDto;
import com.heroku.spacey.services.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;


    @GetMapping("/{cartId}")
    public CheckoutDto getCheckoutByCartId(@PathVariable Long cartId) throws SQLException {

        return checkoutService.getCheckoutByCartId(cartId);
    }
}
