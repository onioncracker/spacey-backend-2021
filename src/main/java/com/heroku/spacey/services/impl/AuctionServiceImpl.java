package com.heroku.spacey.services.impl;

import com.heroku.spacey.dto.auction.AuctionDto;
import com.heroku.spacey.services.AuctionService;

import java.util.List;

public class AuctionServiceImpl implements AuctionService {
    @Override
    public List<AuctionDto> getAll() {
        return null;
    }

    @Override
    public AuctionDto getById(Long id) {
        return null;
    }

    @Override
    public Long add(AuctionDto auctionDto) {
        return null;
    }

    @Override
    public void update(AuctionDto auctionDto, Long id) {

    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public void bidDecreasePrice() {

    }

    @Override
    public void bidIncreasePrice() {

    }
}
