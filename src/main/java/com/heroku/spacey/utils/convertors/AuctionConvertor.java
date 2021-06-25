package com.heroku.spacey.utils.convertors;

import com.heroku.spacey.dto.auction.AuctionDto;
import com.heroku.spacey.dto.auction.DecreaseAuctionDto;
import com.heroku.spacey.dto.auction.IncreaseAuctionDto;
import com.heroku.spacey.entity.Auction;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AuctionConvertor {
    private final ModelMapper mapper = new ModelMapper();

    public Auction adapt(AuctionDto source) {
        mapper.typeMap(AuctionDto.class, Auction.class);
        return mapper.map(source, Auction.class);
    }

    public AuctionDto adapt(Auction source) {
        mapper.typeMap(Auction.class, AuctionDto.class);
        return mapper.map(source, AuctionDto.class);
    }

    public Auction adapt(IncreaseAuctionDto source) {
        mapper.typeMap(IncreaseAuctionDto.class, Auction.class);
        return mapper.map(source, Auction.class);
    }

    public Auction adapt(DecreaseAuctionDto source) {
        mapper.typeMap(DecreaseAuctionDto.class, Auction.class);
        return mapper.map(source, Auction.class);
    }
}
