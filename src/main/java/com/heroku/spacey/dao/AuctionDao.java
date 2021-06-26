package com.heroku.spacey.dao;

import com.heroku.spacey.dto.auction.AuctionDto;
import com.heroku.spacey.entity.Auction;

import java.util.List;

public interface AuctionDao {
    List<AuctionDto> getAllByTypeAuctions(Boolean type);

    List<AuctionDto> getAllAuctions();

    boolean isExist(Long id);

    AuctionDto getById(Long id);

    Long insert(Auction auction);

    void update(Auction auction);

    void delete(Long id);
}
