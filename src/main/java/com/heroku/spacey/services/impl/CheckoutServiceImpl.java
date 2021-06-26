package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.CheckoutDao;
import com.heroku.spacey.dto.order.CheckoutDto;
import com.heroku.spacey.services.CheckoutService;
import com.heroku.spacey.dto.product.ProductCheckoutDto;
import com.heroku.spacey.utils.security.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {

    private final CheckoutDao checkoutDao;
    private final SecurityUtils securityUtils;


    // TODO: add scenery for unauthorized user
    @Override
    public CheckoutDto getCheckout() {
        Long userId = securityUtils.getUserIdByToken();
        CheckoutDto checkoutDto = checkoutDao.getCheckoutInfoByUserId(userId);

        List<ProductCheckoutDto> products = checkoutDao.getProductsFromCartByUserId(userId);
        checkoutDto.getProducts().addAll(products);

        return checkoutDto;
    }

    @Override
    public CheckoutDto getAuctionCheckout(Long auctionId) {
        Long userId = securityUtils.getUserIdByToken();

        return checkoutDao.getAuctionCheckoutByAuctionId(auctionId, userId);
    }

//    @Override
//    public void getAvailableTimeSlots() {
//        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
//        List<Timestamp> timeSlotsForNextThreeDays = new ArrayList<>();
//
//    }
}
