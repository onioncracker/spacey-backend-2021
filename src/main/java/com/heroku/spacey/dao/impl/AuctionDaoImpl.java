package com.heroku.spacey.dao.impl;

import com.heroku.spacey.dao.AuctionDao;
import com.heroku.spacey.entity.Auction;
import com.heroku.spacey.mapper.auction.AuctionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Repository
@PropertySource("classpath:sql/auction_queries.properties")
public class AuctionDaoImpl implements AuctionDao {
    private AuctionMapper mapper = new AuctionMapper();
    private final JdbcTemplate jdbcTemplate;

    @Value("${get_all_decrease_auctions}")
    private String getAllDecreaseAuctions;
    @Value("${get_all_increase_auctions}")
    private String getAllIncreaseAuctions;
    @Value("${get_all_auctions}")
    private String getAllAuctions;
    @Value("${auction_get_by_id}")
    private String getAuctionById;
    @Value("${auction_is_exist}")
    private String isExistAuction;
    @Value("${insert_auction}")
    private String editAuction;
    @Value("${update_auction}")
    private String updateAuction;
    @Value("${delete_auction}")
    private String deleteAuction;

    public AuctionDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Auction> getAllDecreaseAuctions() {
        List<Auction> auctions = Objects.requireNonNull(jdbcTemplate).query(getAllDecreaseAuctions, mapper);
        if (auctions.isEmpty()) {
            return new ArrayList<>();
        }
        return auctions;
    }

    @Override
    public List<Auction> getAllIncreaseAuctions() {
        List<Auction> auctions = Objects.requireNonNull(jdbcTemplate).query(getAllIncreaseAuctions, mapper);
        if (auctions.isEmpty()) {
            return new ArrayList<>();
        }
        return auctions;
    }

    @Override
    public List<Auction> getAllAuctions() {
        List<Auction> auctions = Objects.requireNonNull(jdbcTemplate).query(getAllAuctions, mapper);
        if (auctions.isEmpty()) {
            return new ArrayList<>();
        }
        return auctions;
    }

    @Override
    public boolean isExist(Long id) {
        List<Integer> auctions = Objects.requireNonNull(jdbcTemplate)
                .query(isExistAuction, (rs, i) -> rs.getInt("auctionId"), id);
        return !auctions.isEmpty();
    }

    @Override
    public Auction getById(Long id) {
        List<Auction> auctions = Objects.requireNonNull(jdbcTemplate).query(getAuctionById, mapper);
        if (auctions.isEmpty()) {
            return null;
        }
        return auctions.get(0);
    }

    //TODO: implement proper insert fields for auction
    @Override
    public Long insert(Auction auction) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(editAuction, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, auction.getUserId());
            ps.setLong(2, auction.getProductId());
            ps.setLong(3, auction.getSizeId());
            ps.setInt(4, auction.getAmount());
            ps.setString(5, auction.getAuctionName());
            ps.setBoolean(6, auction.getAuctionType());
            ps.setDouble(7, auction.getStartPrice());
            ps.setDouble(8, auction.getEndPrice());
            ps.setDouble(9, auction.getPriceStep());
            ps.setDouble(10, auction.getBuyPrice());
            ps.setDate(11, auction.getStartTime());
            ps.setDate(12, auction.getEndTime());
            ps.setString(13, auction.getStatus());

            return ps;
        }, holder);
        return (Long) Objects.requireNonNull(holder.getKeys()).get("auctionId");
    }

    //TODO: implement proper update fields for auction
    @Override
    public void update(Auction auction) {
        Object[] params = new Object[]{
                auction.getProductId(), auction.getSizeId(), auction.getAmount(),
                auction.getAuctionName(), auction.getAuctionType(), auction.getStartPrice(),
                auction.getEndPrice(), auction.getPriceStep(), auction.getBuyPrice(),
                auction.getStartTime(), auction.getEndTime(), auction.getStatus(),
                auction.getAuctionId()
        };
        Objects.requireNonNull(jdbcTemplate).update(updateAuction, params);
    }

    @Override
    public void delete(Long id) {
        Objects.requireNonNull(jdbcTemplate).update(deleteAuction, id);
    }
}
