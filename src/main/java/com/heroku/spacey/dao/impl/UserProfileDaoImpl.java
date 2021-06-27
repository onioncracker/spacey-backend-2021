package com.heroku.spacey.dao.impl;

import com.heroku.spacey.dao.UserProfileDao;
import com.heroku.spacey.dto.user.UserProfileDto;
import com.heroku.spacey.mapper.UserProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
@RequiredArgsConstructor
@PropertySource("classpath:sql/user_profile_queries.properties")
public class UserProfileDaoImpl implements UserProfileDao {

    private final JdbcTemplate jdbcTemplate;

    @Value("${get_user_info}")
    private String sqlGetUserInfo;

    @Value("${update_user_info}")
    private String sqlUpdateUserInfo;

    @Override
    public UserProfileDto getUserInfo(Long userId) {
        UserProfileMapper mapper = new UserProfileMapper();
        return jdbcTemplate.query(sqlGetUserInfo, mapper, userId);
    }

    @Override
    public void updateUserInfo(UserProfileDto userProfileDto, Long userId) {

        String firstName = userProfileDto.getFirstName();
        String lastName = userProfileDto.getLastName();
        String phoneNumber = userProfileDto.getPhoneNumber();
        Date dateOfBirth = userProfileDto.getDateOfBirth();
        String sex = userProfileDto.getSex();
        String city = userProfileDto.getCity();
        String street = userProfileDto.getStreet();
        String house = userProfileDto.getHouse();
        String apartment = userProfileDto.getApartment();

        Object[] param = new Object[]{firstName, lastName, phoneNumber, dateOfBirth, sex, city, street, house, apartment, userId};

        jdbcTemplate.update(sqlUpdateUserInfo, param);
    }
}
