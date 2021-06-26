package com.heroku.spacey.dto.auction;

import com.heroku.spacey.dto.product.ProductDto;
import com.heroku.spacey.dto.size.SizeDto;
import com.heroku.spacey.dto.user.UserDto;
import lombok.Data;

import java.sql.Date;

@Data
public class TypeAuctionDto {
    private Long auctionId;

    private UserDto user;
    private ProductDto product;
    private SizeDto size;

    private Integer amount;
    private String auctionName;
    private Boolean auctionType;
    private Double startPrice;
    private Double endPrice;
    private Double priceStep;
    private Double buyPrice;
    private Date startTime;
    private String status;
}
