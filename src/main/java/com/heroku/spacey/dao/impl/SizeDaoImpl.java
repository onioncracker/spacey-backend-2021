package com.heroku.spacey.dao.impl;

import com.heroku.spacey.dao.SizeDao;
import com.heroku.spacey.entity.Category;
import com.heroku.spacey.entity.Size;
import com.heroku.spacey.mapper.SizeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Slf4j
@Repository
@PropertySource("classpath:sql/size_queries.properties")
public class SizeDaoImpl implements SizeDao {
    private final SizeMapper mapper = new SizeMapper();
    private final JdbcTemplate jdbcTemplate;

    @Value("${get_all_sizes}")
    private String getAllSizes;
    @Value("${size_get_by_id}")
    private String getSizeById;
    @Value("${insert_size}")
    private String editSize;
    @Value("${update_size}")
    private String updateSize;
    @Value("${delete_size}")
    private String deleteSize;

    public SizeDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Size> getAllSizes() {
        List<Size> sizes = Objects.requireNonNull(jdbcTemplate).query(getAllSizes, mapper);
        if (sizes.isEmpty()) {
            return null;
        }
        return sizes;
    }

    @Override
    public Size getById(Long id) {
        return Objects.requireNonNull(jdbcTemplate).queryForObject(getSizeById, mapper, id);
    }

    @Override
    public Long insert(Size size) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(editSize, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, size.getName());
            ps.setLong(2, size.getQuantity());
            return ps;
        }, holder);
        return (Long) Objects.requireNonNull(holder.getKeys()).get("sizeId");
    }

    @Override
    public void update(Size size) {
        Object[] params = new Object[]{size.getName(), size.getId(), size.getQuantity()};
        Objects.requireNonNull(jdbcTemplate).update(updateSize, params);
    }

    @Override
    public void delete(Long id) {
        Objects.requireNonNull(jdbcTemplate).update(deleteSize, id);
    }
}
