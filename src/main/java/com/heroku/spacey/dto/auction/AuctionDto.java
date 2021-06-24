package com.heroku.spacey.dto.auction;

import com.heroku.spacey.dto.product.ProductDto;
import com.heroku.spacey.dto.size.SizeDto;
import com.heroku.spacey.dto.user.UserDto;
import lombok.Data;

import java.sql.Date;

@Data
public class AuctionDto {
    private Long auctionId;

    private UserDto auctionUser;
    private ProductDto product;
    private SizeDto size;

    private String auctionName;
    private Boolean auctionType;
    private Double startPrice;
    private Double endPrice;
    private Double priceStep;
    private Date startTime;
    private Date endTime;
    private String status;
    private Integer amount;
}
