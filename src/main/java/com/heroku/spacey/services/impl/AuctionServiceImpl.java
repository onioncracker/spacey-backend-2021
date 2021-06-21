package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.AuctionDao;
import com.heroku.spacey.dto.auction.AuctionDto;
import com.heroku.spacey.entity.Auction;
import com.heroku.spacey.services.AuctionService;
import com.heroku.spacey.utils.convertors.AuctionConvertor;
import com.heroku.spacey.utils.convertors.CommonConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.webjars.NotFoundException;

import java.util.List;

public class AuctionServiceImpl implements AuctionService {
    private final AuctionDao auctionDao;
    private final CommonConvertor commonConvertor;
    private final AuctionConvertor auctionConvertor;

    public AuctionServiceImpl(@Autowired AuctionDao auctionDao,
                              @Autowired CommonConvertor commonConvertor,
                              @Autowired AuctionConvertor auctionConvertor) {
        this.auctionDao = auctionDao;
        this.commonConvertor = commonConvertor;
        this.auctionConvertor = auctionConvertor;
    }

    //TODO: implement getting all decrease auctions
    @Override
    public List<AuctionDto> getAllDecrease() {
        return null;
    }

    //TODO: implement getting all increase auctions
    @Override
    public List<AuctionDto> getAllIncrease() {
        return null;
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
            throw new NotFoundException("Product not found");
        }
        return auctionConvertor.adapt(auction);
    }

    //TODO: implement addition for auction
    @Override
    public Long add(AuctionDto auctionDto) {
        return null;
    }

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
            new ResponseEntity(HttpStatus.NOT_FOUND);
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
