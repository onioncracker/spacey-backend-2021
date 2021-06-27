package com.heroku.spacey.services;

import com.heroku.spacey.dto.auction.AuctionDto;
import com.heroku.spacey.dto.auction.AllAuctionsDto;

import java.util.List;

public interface AuctionService {
    List<AllAuctionsDto> getAllByType(Boolean type);

    List<AllAuctionsDto> getAll();

    AuctionDto getById(Long id);

    Long add(AuctionDto auctionDto);

    void update(AuctionDto auctionDto, Long id);

    void remove(Long id);
}
