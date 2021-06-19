package com.heroku.spacey.dto.auction;

import com.heroku.spacey.dto.user.UserRegisterDto;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class DecreaseAuctionDto {
    private Long auctionId;
    private List<UserRegisterDto> users;
    private String auctionName;
    private Boolean auctionType;
    private Long startPrice;
    private Long endPrice;
    private Timestamp startTime;
    private Timestamp endTime;
    private String status;
}
