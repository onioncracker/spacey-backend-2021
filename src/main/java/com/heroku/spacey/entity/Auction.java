package com.heroku.spacey.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class Auction {
    private Long auctionId;

    private User auctionUser;
    private Long userId;

    private String auctionName;
    private Boolean auctionType;
    private Double startPrice;
    private Double endPrice;
    private Double priceStep;
    private Date startTime;
    private Date endTime;
    private String status;
}
