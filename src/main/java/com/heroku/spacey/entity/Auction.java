package com.heroku.spacey.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Auction {
    private Long auctionId;

    private User auctionBuyer;
    private Long userId;

    private Product auctionProduct;
    private Long productId;

    private Size productSize;
    private Long sizeId;

    private Integer amount;
    private String auctionName;
    private Boolean auctionType;
    private Double startPrice;
    private Double endPrice;
    private Double priceStep;
    private Double buyPrice;
    private Timestamp startTime;
    private Timestamp endTime;
    private String status;
}
