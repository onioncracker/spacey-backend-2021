package com.heroku.spacey.dao.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public abstract class BaseDao extends JdbcDaoSupport {

    public BaseDao(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public int add(PreparedStatement statement) {
        try {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Not affected rows!");
            }
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                } else {
                    throw new SQLException("error!");
                }
            }
        } catch (SQLException throwable) {
            log.error(throwable.getMessage());
        }
        return -1;
    }
}
