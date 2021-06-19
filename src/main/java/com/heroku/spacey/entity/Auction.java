package com.heroku.spacey.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Auction {
    private Long auctionId;
    private Long userId;

    private String auctionName;
    private Boolean auctionType;
    private Double startPrice;
    private Double endPrice;
    private Double priceStep;
    private Timestamp startTime;
    private Timestamp endTime;
    private String status;
}
