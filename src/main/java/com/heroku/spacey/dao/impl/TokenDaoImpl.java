package com.heroku.spacey.dao.impl;

import com.heroku.spacey.dao.TokenDao;
import com.heroku.spacey.entity.User;
import com.heroku.spacey.entity.Token;
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
public class TokenDaoImpl implements TokenDao {
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

    public TokenDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Token findByToken(String token) {
        List<Token> tokens = jdbcTemplate.query(findByToken, mapper, token);
        if (tokens.isEmpty()) {
            return null;
        }
        return tokens.get(0);
    }

    @Override
    public Token findByTokenId(Long id) {
        List<Token> tokens = jdbcTemplate.query(findByTokenId, mapper, id);
        if (tokens.isEmpty()) {
            return null;
        }
        return tokens.get(0);
    }

    @Override
    public Token findByUser(User user) {
        List<Token> tokens = jdbcTemplate.query(findByUser, mapper, user);
        if (tokens.isEmpty()) {
            return null;
        }
        return tokens.get(0);
    }

    @Override
    public Long insert(Token token) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertToken, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, token.getConfirmationToken());
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
