package com.heroku.spacey.dto.auction;

import com.heroku.spacey.dto.size.SizeDto;
import lombok.Data;

import java.sql.Date;

@Data
public class AuctionDto {
    private Long auctionId;

    private Long userId;
    private Long productId;
    private Long sizeId;
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
