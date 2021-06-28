package com.heroku.spacey.dto.auction;

import com.heroku.spacey.dto.size.SizeDto;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class AuctionDto {
    private Long auctionId;

    private Long userId;
    private AuctionProductDto auctionProduct;

    private SizeDto productSize;
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
