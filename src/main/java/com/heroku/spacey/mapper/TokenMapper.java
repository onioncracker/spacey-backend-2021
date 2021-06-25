package com.heroku.spacey.mapper;

import com.heroku.spacey.entity.Token;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TokenMapper implements RowMapper<Token> {
    @Override
    public Token mapRow(ResultSet resultSet, int i) throws SQLException {
        Token token = new Token();
        token.setTokenId(resultSet.getLong("tokenid"));
        token.setToken(resultSet.getString("confirmationtoken"));
        token.setDate(resultSet.getTimestamp("date"));
        return token;
    }
}
