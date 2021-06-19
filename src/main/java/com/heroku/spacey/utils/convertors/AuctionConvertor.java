package com.heroku.spacey.utils.convertors;

import com.heroku.spacey.dto.auction.DecreaseAuctionDto;
import com.heroku.spacey.entity.Auction;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AuctionConvertor {
    private final ModelMapper mapper = new ModelMapper();

    public Auction adapt(DecreaseAuctionDto source) {
        mapper.typeMap(DecreaseAuctionDto.class, Auction.class);
        return mapper.map(source, Auction.class);
    }

    public DecreaseAuctionDto adapt(Auction source) {
        mapper.typeMap(Auction.class, DecreaseAuctionDto.class);
        return mapper.map(source, DecreaseAuctionDto.class);
    }
}
