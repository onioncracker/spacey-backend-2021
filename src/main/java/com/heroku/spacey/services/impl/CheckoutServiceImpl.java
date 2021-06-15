package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.CheckoutDao;
import com.heroku.spacey.dto.order.CheckoutDto;
import com.heroku.spacey.services.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {

    private final CheckoutDao checkoutDao;


    @Override
    public CheckoutDto getCheckoutByCartId(Long cartId) {
        return checkoutDao.getByCartId(cartId);
    }
}
