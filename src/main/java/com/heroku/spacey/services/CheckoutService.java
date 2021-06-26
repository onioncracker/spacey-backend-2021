package com.heroku.spacey.services;

import com.heroku.spacey.dto.order.CheckoutDto;

public interface CheckoutService {

    CheckoutDto getCheckout();

    CheckoutDto getAuctionCheckout(Long auctionId);
}
