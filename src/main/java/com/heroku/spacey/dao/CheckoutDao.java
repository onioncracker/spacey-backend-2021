package com.heroku.spacey.dao;

import com.heroku.spacey.dto.order.CheckoutDto;
import com.heroku.spacey.dto.product.ProductCheckoutDto;

import java.util.List;

public interface CheckoutDao {

    List<ProductCheckoutDto> getProductsFromCartByUserId(Long userId);

    CheckoutDto getCheckoutInfoByUserId(Long userId);

    CheckoutDto getAuctionCheckoutByAuctionId(Long auctionId, Long userId);
}
