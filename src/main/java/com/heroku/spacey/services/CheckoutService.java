package com.heroku.spacey.services;

import com.heroku.spacey.dto.order.CheckoutDto;

public interface CheckoutService {

    CheckoutDto getCheckoutByCartId(Long cartId);

    CheckoutDto getCheckoutByUserId();
}
