package com.heroku.spacey.dto.auction;

import lombok.Data;

import java.sql.Date;

@Data
public class AuctionDto {
    private Long auctionId;

    private Long userId;
    private AuctionProductDto product;

    private String sizeName;
    private Integer amount;
    private String auctionName;
    private Boolean auctionType;
    private Double startPrice;
    private Double endPrice;
    private Double priceStep;
    private Double buyPrice;
    private Date startTime;
    private Date endTime;
    private String status;
}
