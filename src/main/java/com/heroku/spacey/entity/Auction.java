package com.heroku.spacey.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class Auction {
    private Long auctionId;

    private User auctionBuyer;
    private Long userId;

    private Product actionProduct;
    private Long productId;

    private Size productSize;
    private Long sizeId;

    private Integer amount;
    private Long packageAmount;
    private String auctionName;
    private Boolean auctionType;
    private Double startPrice;
    private Double endPrice;
    private Double priceStep;
    private Date startTime;
    private Date endTime;
    private String status;
}
