package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.AuctionDao;
import com.heroku.spacey.dto.auction.AuctionDto;
import com.heroku.spacey.dto.auction.DecreaseAuctionDto;
import com.heroku.spacey.dto.auction.IncreaseAuctionDto;
import com.heroku.spacey.entity.Auction;
import com.heroku.spacey.services.AuctionService;
import com.heroku.spacey.utils.convertors.AuctionConvertor;
import com.heroku.spacey.utils.convertors.CommonConvertor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuctionServiceImpl implements AuctionService {
    private final AuctionDao auctionDao;
    private final CommonConvertor commonConvertor;
    private final AuctionConvertor auctionConvertor;

    @Override
    public List<DecreaseAuctionDto> getAllDecrease() {
        List<Auction> decreaseAuctions = auctionDao.getAllDecreaseAuctions();
        return commonConvertor.mapList(decreaseAuctions, DecreaseAuctionDto.class);
    }

    @Override
    public List<IncreaseAuctionDto> getAllIncrease() {
        List<Auction> increaseAuctions = auctionDao.getAllIncreaseAuctions();
        return commonConvertor.mapList(increaseAuctions, IncreaseAuctionDto.class);
    }

    @Override
    public List<AuctionDto> getAll() {
        List<Auction> auctions = auctionDao.getAllAuctions();
        return commonConvertor.mapList(auctions, AuctionDto.class);
    }

    @Override
    public AuctionDto getById(Long id) {
        Auction auction = auctionDao.getById(id);
        if (auction == null) {
            throw new NotFoundException("Auction not found");
        }
        return auctionConvertor.adapt(auction);
    }

    @Override
    public Long add(AuctionDto auctionDto) {
        Auction auction = auctionConvertor.adapt(auctionDto);

        Long userId = auctionDto.getAuctionUser().getId();
        auction.setUserId(userId);

        Long productId = auctionDto.getProduct().getId();
        auction.setProductId(productId);

        Long sizeId = auctionDto.getSize().getId();
        auction.setSizeId(sizeId);

        return auctionDao.insert(auction);
    }

    // TODO: fix update method
    @Override
    public void update(AuctionDto auctionDto, Long id) {
        Auction auction = auctionConvertor.adapt(auctionDto);
        auction.setAuctionId(id);
        auctionDao.update(auction);
    }

    @Override
    public void remove(Long id) {
        boolean isFound = auctionDao.isExist(id);
        if (!isFound) {
            throw new NotFoundException("Auction not found");
        }
        auctionDao.delete(id);
    }

    //TODO: implement decrease price bid for auction
    @Override
    public void bidDecreasePrice() {

    }

    //TODO: implement increase price bid for auction
    @Override
    public void bidIncreasePrice() {

    }
}
