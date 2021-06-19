package com.heroku.spacey.services;

import com.heroku.spacey.dto.auction.DecreaseAuctionDto;

import java.util.List;

public interface DecreaseAuctionService {
    List<DecreaseAuctionDto> getAllDecreaseAuctions();

    DecreaseAuctionDto getById(Long id);

    Long addDecreaseAuction(DecreaseAuctionDto decreaseAuctionDto);

    void updateDecreaseAuction(DecreaseAuctionDto decreaseAuctionDto, Long id);

    void removeDecreaseAuction(Long id);
}
