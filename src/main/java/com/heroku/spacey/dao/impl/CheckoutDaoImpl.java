package com.heroku.spacey.dao.impl;

import com.heroku.spacey.dao.CheckoutDao;
import com.heroku.spacey.dto.order.CheckoutDto;
import com.heroku.spacey.dto.product.ProductCheckoutDto;
import com.heroku.spacey.mapper.order.CheckoutMapper;
import com.heroku.spacey.mapper.order.ProductCheckoutMapper;
import lombok.RequiredArgsConstructor;
import org.webjars.NotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
@PropertySource("classpath:sql/checkout_queries.properties")
public class CheckoutDaoImpl implements CheckoutDao {

    private final CheckoutMapper checkoutMapper;
    private final ProductCheckoutMapper productCheckoutMapper;
    private final JdbcTemplate jdbcTemplate;

    @Value("${select_products_from_cart_by_user_id}")
    private String sqlSelectProductsFromCartByUserId;
    @Value("${select_checkout_info_by_user_id}")
    private String sqlSelectCheckoutInfoByUserId;
    @Value("${select_auction_checkout_by_auction_id}")
    private String sqlSelectAuctionCheckoutByAuctionId;


    @Override
    public List<ProductCheckoutDto> getProductsFromCartByUserId(Long userId) {
        return Objects.requireNonNull(jdbcTemplate)
                .query(sqlSelectProductsFromCartByUserId,
                       productCheckoutMapper,
                       userId);
    }

    @Override
    public CheckoutDto getCheckoutInfoByUserId(Long userId) {
        List<CheckoutDto> checkoutInfos = Objects.requireNonNull(jdbcTemplate).query(sqlSelectCheckoutInfoByUserId,
                                                                                     checkoutMapper,
                                                                                     userId);
        if (checkoutInfos.isEmpty()) {
            throw new NotFoundException("Haven't found checkout info for the user.");
        }

        return checkoutInfos.get(0);
    }

    @Override
    public CheckoutDto getAuctionCheckoutByAuctionId(Long auctionId, Long userId) {
        List<CheckoutDto> checkoutDtos = Objects.requireNonNull(jdbcTemplate).query(sqlSelectAuctionCheckoutByAuctionId,
                                                                                    checkoutMapper,
                                                                                    auctionId, userId);
        if (checkoutDtos.isEmpty()) {
            throw new NotFoundException("Haven't found checkout info for such auction.");
        }

        return checkoutDtos.get(0);
    }
}
