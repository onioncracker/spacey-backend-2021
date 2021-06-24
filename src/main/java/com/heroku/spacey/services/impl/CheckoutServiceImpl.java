package com.heroku.spacey.services.impl;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import com.heroku.spacey.dao.CheckoutDao;
import org.springframework.stereotype.Service;
import com.heroku.spacey.dto.order.CheckoutDto;
import com.heroku.spacey.services.CheckoutService;
import com.heroku.spacey.utils.security.SecurityUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {
    private final CheckoutDao checkoutDao;
    private final SecurityUtils securityUtils;

    @Override
    public CheckoutDto getCheckoutByCartId(Long cartId) {
        return checkoutDao.getByCartId(cartId);
    }

    @Override
    public CheckoutDto getCheckoutByUserId() {
        Long userId = securityUtils.getUserIdByToken();
        return checkoutDao.getByUserId(userId);
    }
}
