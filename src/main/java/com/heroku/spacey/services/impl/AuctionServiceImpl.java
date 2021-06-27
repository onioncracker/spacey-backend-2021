package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.AuctionDao;
import com.heroku.spacey.dto.auction.AuctionDto;
import com.heroku.spacey.dto.auction.AllAuctionsDto;
import com.heroku.spacey.entity.Auction;
import com.heroku.spacey.services.AuctionService;
import com.heroku.spacey.utils.convertors.AuctionConvertor;
import com.heroku.spacey.utils.convertors.CommonConvertor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuctionServiceImpl implements AuctionService {
    private final AuctionDao auctionDao;
    private final CommonConvertor commonConvertor;
    private final AuctionConvertor auctionConvertor;

    @Override
    public List<AllAuctionsDto> getAllByType(Boolean type) {
        List<Auction> auctions = auctionDao.getAllByTypeAuctions(type);
        return commonConvertor.mapList(auctions, AllAuctionsDto.class);
    }

    @Override
    public List<AllAuctionsDto> getAll() {
        List<Auction> auctions = auctionDao.getAllAuctions();
        return commonConvertor.mapList(auctions, AllAuctionsDto.class);
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
    @Transactional
    public Long add(AuctionDto auctionDto) {
        Auction auction = auctionConvertor.adapt(auctionDto);

        Long productId = auctionDto.getAuctionProduct().getId();
        auction.setProductId(productId);

        Long sizeId = auctionDto.getProductSize().getId();
        auction.setSizeId(sizeId);

        return auctionDao.insert(auction);
    }

    @Override
    @Transactional
    public void update(AuctionDto auctionDto, Long id) {
        Auction auction = auctionConvertor.adapt(auctionDto);
        auction.setAuctionId(id);
        auctionDao.update(auction);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        boolean isFound = auctionDao.isExist(id);
        if (!isFound) {
            throw new NotFoundException("Auction not found");
        }
        auctionDao.delete(id);
    }

    //TODO: implement decrease price bid for auction
    @Override
    public void bidDecreasePrice(AllAuctionsDto allAuctionsDto) {

    }

    //TODO: implement increase price bid for auction
    @Override
    public void bidIncreasePrice() {

    }
}
