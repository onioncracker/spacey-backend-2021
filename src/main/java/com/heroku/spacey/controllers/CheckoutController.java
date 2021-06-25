package com.heroku.spacey.controllers;

import com.heroku.spacey.dto.order.CheckoutDto;
import com.heroku.spacey.services.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;


    @Secured("ROLE_USER")
    @GetMapping("/api/checkout")
    public CheckoutDto getCheckout() {
        return checkoutService.getCheckout();
    }

    @Secured("ROLE_USER")
    @GetMapping("/api/auction-checkout/{auctionId}")
    public CheckoutDto getAuctionCheckout(@PathVariable Long auctionId) {
        return checkoutService.getAuctionCheckout(auctionId);
    }
}
