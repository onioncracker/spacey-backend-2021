package com.heroku.spacey.dto.auction;

import com.heroku.spacey.dto.product.ProductDto;
import com.heroku.spacey.dto.user.UserDto;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class IncreaseAuctionDto {
    private Long auctionId;

    private UserDto users;

    private String auctionName;
    private Boolean auctionType;
    private Double startPrice;
    private Double priceStep;
    private Date startTime;
    private Date endTime;
    private String status;

    private List<ProductDto> products;
}
