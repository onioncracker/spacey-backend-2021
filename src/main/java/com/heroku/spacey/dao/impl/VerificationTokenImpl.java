package com.heroku.spacey.dao.impl;

import com.heroku.spacey.dao.VerificationTokenDao;
import com.heroku.spacey.entity.User;
import com.heroku.spacey.entity.VerificationToken;
import com.heroku.spacey.mapper.TokenMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Slf4j
@Repository
@PropertySource("classpath:sql/token_queries.properties")
public class VerificationTokenImpl implements VerificationTokenDao {
    private final TokenMapper mapper = new TokenMapper();
    private final JdbcTemplate jdbcTemplate;

    @Value("${find_by_token}")
    private String findByToken;
    @Value("${find_by_id}")
    private String findByTokenId;
    @Value("${find_by_user}")
    private String findByUser;
    @Value("${insert_token}")
    private String insertToken;
    @Value("${delete_expired_tokens}")
    private String deleteExpiredTokens;
    @Value("${delete_token}")
    private String deleteToken;

    public VerificationTokenImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public VerificationToken findByToken(String token) {
        List<VerificationToken> tokens = jdbcTemplate.query(findByToken, mapper, token);
        if (tokens.isEmpty()) {
            return null;
        }
        return tokens.get(0);
    }

    @Override
    public VerificationToken findByTokenId(Long id) {
        List<VerificationToken> tokens = jdbcTemplate.query(findByTokenId, mapper, id);
        if (tokens.isEmpty()) {
            return null;
        }
        return tokens.get(0);
    }

    @Override
    public VerificationToken findByUser(User user) {
        List<VerificationToken> tokens = jdbcTemplate.query(findByUser, mapper, user);
        if (tokens.isEmpty()) {
            return null;
        }
        return tokens.get(0);
    }

    @Override
    public Long insert(VerificationToken verificationToken) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertToken, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, verificationToken.getConfirmationToken());
            return ps;
        }, holder);
        return (Long) Objects.requireNonNull(holder.getKeys()).get("tokenid");
    }

    @Override
    public void deleteAllExpiredSince(Date now) {
        Objects.requireNonNull(jdbcTemplate).update(deleteExpiredTokens, now);
    }

    @Override
    public void delete(Long id) {
        Objects.requireNonNull(jdbcTemplate).update(deleteToken, id);
    }
}
