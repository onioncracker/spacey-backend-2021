package com.heroku.spacey.dao.impl;

import com.heroku.spacey.dao.AuctionDao;
import com.heroku.spacey.entity.Auction;
import com.heroku.spacey.mapper.auction.all.AuctionAllMapper;
import com.heroku.spacey.mapper.auction.by_id.AuctionIdMapper;
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
    private AuctionIdMapper mapById = new AuctionIdMapper();
    private AuctionAllMapper mapAll = new AuctionAllMapper();
    private final JdbcTemplate jdbcTemplate;

    @Value("${get_all_by_type_auctions}")
    private String getAllByTypeAuctions;
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
    public List<Auction> getAllByTypeAuctions(Boolean type) {
        List<Auction> auctions = Objects.requireNonNull(jdbcTemplate).query(getAllByTypeAuctions, mapAll, type);
        if (auctions.isEmpty()) {
            return new ArrayList<>();
        }
        return auctions;
    }

    @Override
    public List<Auction> getAllAuctions() {
        List<Auction> auctions = Objects.requireNonNull(jdbcTemplate).query(getAllAuctions, mapAll);
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
        List<Auction> auctions = Objects.requireNonNull(jdbcTemplate).query(getAuctionById, mapById, id);
        if (auctions.isEmpty()) {
            return null;
        }
        return auctions.get(0);
    }

    @Override
    public Long insert(Auction auction) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(editAuction, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, auction.getProductId());
            ps.setLong(2, auction.getSizeId());
            ps.setInt(3, auction.getAmount());
            ps.setString(4, auction.getAuctionName());
            ps.setBoolean(5, auction.getAuctionType());
            ps.setDouble(6, auction.getStartPrice());
            ps.setDouble(7, auction.getEndPrice());
            ps.setDouble(8, auction.getPriceStep());
            ps.setTimestamp(9, auction.getStartTime());
            ps.setTimestamp(10, auction.getEndTime());
            ps.setString(11, auction.getStatus());

            return ps;
        }, holder);
        return (Long) Objects.requireNonNull(holder.getKeys()).get("auctionId");
    }

    @Override
    public void update(Auction auction) {
        Object[] params = new Object[]{
                auction.getProductId(), auction.getSizeId(), auction.getAmount(),
                auction.getAuctionName(), auction.getAuctionType(), auction.getStartPrice(),
                auction.getEndPrice(), auction.getPriceStep(), auction.getStartTime(),
                auction.getEndTime(), auction.getStatus(), auction.getAuctionId()
        };
        Objects.requireNonNull(jdbcTemplate).update(updateAuction, params);
    }

    @Override
    public void delete(Long id) {
        Objects.requireNonNull(jdbcTemplate).update(deleteAuction, id);
    }
}
