package com.heroku.spacey.mapper;

import com.heroku.spacey.dto.user.UserProfileDto;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserProfileMapper implements ResultSetExtractor<UserProfileDto> {

    @Override
    public UserProfileDto extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        UserProfileDto user = new UserProfileDto();

        if (!resultSet.next()) {
            throw new NotFoundException("User not found!");
        }

        user.setUserId(resultSet.getLong("userId"));
        user.setFirstName(resultSet.getString("firstName"));
        user.setLastName(resultSet.getString("lastName"));
        user.setPhoneNumber(resultSet.getString("phoneNumber"));
        user.setDateOfBirth(resultSet.getDate("dateOfBirth"));
        user.setSex(resultSet.getString("sex"));
        user.setCity(resultSet.getString("city"));
        user.setStreet(resultSet.getString("street"));
        user.setHouse(resultSet.getString("house"));
        user.setApartment(resultSet.getString("appartment"));
        user.setEmail(resultSet.getString("email"));

        return user;
    }
}
