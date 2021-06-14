package com.heroku.spacey.services;

import com.heroku.spacey.dto.order.CheckoutDto;

import java.sql.SQLException;

public interface CheckoutService {

    CheckoutDto getCheckoutByCartId(Long cartId) throws SQLException;
}
