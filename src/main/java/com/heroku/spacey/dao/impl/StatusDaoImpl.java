package com.heroku.spacey.dao.impl;

import com.heroku.spacey.dao.StatusDao;
import com.heroku.spacey.entity.Status;
import com.heroku.spacey.mapper.StatusMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Objects;

@Slf4j
@Repository
@PropertySource("classpath:sql/status_queries.properties")
public class StatusDaoImpl implements StatusDao {

    private final JdbcTemplate jdbcTemplate;
    private StatusMapper mapper;

    @Value("${get_status_by_statusname}")
    private String getStatusByStatusName;

    @Value("${get_status_by_id}")
    private String getStatusById;

    @Value("${insert_status}")
    private String insertStatus;

    public StatusDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = new StatusMapper();
    }

    @Override
    public Long getStatusId(String statusName) {
        return DataAccessUtils.singleResult(jdbcTemplate.query(getStatusByStatusName,
            SingleColumnRowMapper.newInstance(Long.class), statusName));
    }

    @Override
    public String getStatusName(long id) {
        Status result = jdbcTemplate.queryForObject(getStatusById, mapper, id);
        if (result == null) {
            return null;
        }
        return result.getStatusName();
    }

    @Override
    public long insertStatus(String statusName) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertStatus, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, statusName);
            return ps;
        }, holder);
        return (long) Objects.requireNonNull(holder.getKeys()).get("statusId");
    }
}
