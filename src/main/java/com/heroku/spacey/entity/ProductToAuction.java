package com.heroku.spacey.entity;

import lombok.Data;

@Data
public class ProductToAuction {
    private Long auctionId;
    private Long productId;
    private Long sizeId;
    private Integer amount;
}
