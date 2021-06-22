package com.heroku.spacey.dao;

import com.heroku.spacey.dto.order.CheckoutDto;

public interface CheckoutDao {

    CheckoutDto getByCartId(Long cartId);

    CheckoutDto getByUserId(Long userId);
}
