package com.heroku.spacey.dao.impl;

import com.heroku.spacey.dao.RoleDao;
import com.heroku.spacey.entity.Role;
import com.heroku.spacey.mapper.RoleMapper;
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
@PropertySource("classpath:sql/role_queries.properties")
public class RoleDaoImpl implements RoleDao {

    private final JdbcTemplate jdbcTemplate;
    private RoleMapper mapper;

    @Value("${get_role_by_rolename}")
    private String getRoleByRoleName;

    @Value("${get_role_by_id}")
    private String getRoleById;

    @Value("${insert_role}")
    private String insertRole;

    public RoleDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = new RoleMapper();
    }

    @Override
    public Long getRoleId(String roleName) {
        return DataAccessUtils.singleResult(jdbcTemplate.query(getRoleByRoleName,
            SingleColumnRowMapper.newInstance(Long.class), roleName));
    }

    @Override
    public String getRoleName(long id) {
        Role result = jdbcTemplate.queryForObject(getRoleById, mapper, id);
        if (result == null) {
            return null;
        }
        return result.getRoleName();
    }

    @Override
    public long insertRole(String roleName) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertRole, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, roleName);
            return ps;
        }, holder);
        return (long) Objects.requireNonNull(holder.getKeys()).get("roleId");
    }
}
