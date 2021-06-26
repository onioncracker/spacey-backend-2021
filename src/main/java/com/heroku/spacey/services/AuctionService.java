package com.heroku.spacey.services;

import com.heroku.spacey.dto.auction.AuctionDto;
import com.heroku.spacey.dto.auction.TypeAuctionDto;

import java.util.List;

public interface AuctionService {
    List<AuctionDto> getAllByType(Boolean type);

    List<AuctionDto> getAll();

    AuctionDto getById(Long id);

    Long add(AuctionDto auctionDto);

    void update(AuctionDto auctionDto, Long id);

    void remove(Long id);

    void bidDecreasePrice(TypeAuctionDto typeAuctionDto);

    void bidIncreasePrice();
}
