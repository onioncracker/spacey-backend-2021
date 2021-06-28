package com.heroku.spacey.dto.auction;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AllAuctionsDto {
    private Long auctionId;

    private AllAuctionsProductDto auctionProduct;

    private Integer amount;
    private String auctionName;
    private Boolean auctionType;
    private Double startPrice;
    private Double endPrice;
    private Double buyPrice;
    private Timestamp startTime;
    private Timestamp endTime;
    private String status;
}
