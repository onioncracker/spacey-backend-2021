package com.heroku.spacey.services;

import com.heroku.spacey.dto.auction.AuctionDto;
import com.heroku.spacey.dto.auction.DecreaseAuctionDto;
import com.heroku.spacey.dto.auction.IncreaseAuctionDto;

import java.util.List;

public interface AuctionService {
    List<DecreaseAuctionDto> getAllDecrease();

    List<IncreaseAuctionDto> getAllIncrease();

    List<AuctionDto> getAll();

    AuctionDto getById(Long id);

    Long add(AuctionDto auctionDto);

    void update(AuctionDto auctionDto, Long id);

    void remove(Long id);

    void bidDecreasePrice(DecreaseAuctionDto decreaseAuctionDto);

    void bidIncreasePrice();
}
