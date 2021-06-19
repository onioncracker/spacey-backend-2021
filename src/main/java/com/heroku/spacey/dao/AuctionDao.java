package com.heroku.spacey.dao;

import com.heroku.spacey.entity.Auction;

import java.util.List;

public interface AuctionDao {
    List<Auction> getAllDecreaseAuctions();

    List<Auction> getAllIncreaseAuctions();

    Auction getById(Long id);

    Long insert(Auction auction);

    void update(Auction auction);

    void delete(Long id);

    void addProductToAuction(Long auctionId, Long productId, Long sizeId, Integer amount);
}
