package com.heroku.spacey.dto.auction;

import com.heroku.spacey.dto.user.UserDto;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class AuctionDto {
    private Long auctionId;

    private UserDto auctionUser;

    private String auctionName;
    private Boolean auctionType;
    private Double startPrice;
    private Double endPrice;
    private Double priceStep;
    private Timestamp startTime;
    private Timestamp endTime;
    private String status;
}
