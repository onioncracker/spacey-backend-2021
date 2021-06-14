package com.heroku.spacey.mapper;

import com.heroku.spacey.entity.VerificationToken;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TokenMapper implements RowMapper<VerificationToken> {
    @Override
    public VerificationToken mapRow(ResultSet resultSet, int i) throws SQLException {
        VerificationToken token = new VerificationToken();
        token.setTokenId(resultSet.getLong("tokenid"));
        token.setConfirmationToken(resultSet.getString("confirmationtoken"));
        token.setDate(resultSet.getTimestamp("date"));
        return token;
    }
}
