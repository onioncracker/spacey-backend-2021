package com.heroku.spacey.dao.impl;

import com.heroku.spacey.dao.ColorDao;
import com.heroku.spacey.entity.Color;
import com.heroku.spacey.mapper.ColorMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Repository
@PropertySource("classpath:sql/color_queries.properties")
public class ColorDaoImpl implements ColorDao {
    private final ColorMapper mapper = new ColorMapper();
    private final JdbcTemplate jdbcTemplate;

    @Value("${get_all_colors}")
    private String getAllColors;
    @Value("${color_get_by_id}")
    private String getColorById;
    @Value("${insert_color}")
    private String editColor;
    @Value("${update_color}")
    private String updateColor;
    @Value("${delete_color}")
    private String deleteColor;

    public ColorDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Color> getAllColors() {
        List<Color> colors = Objects.requireNonNull(jdbcTemplate).query(getAllColors, mapper);
        if (colors.isEmpty()) {
            return new ArrayList<>();
        }
        return colors;
    }

    @Override
    public Color getById(Long id) {
        List<Color> colors = Objects.requireNonNull(jdbcTemplate).query(getColorById, mapper, id);
        if (colors.isEmpty()) {
            return null;
        }
        return colors.get(0);
    }

    @Override
    public Long insert(Color color) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(editColor, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, color.getName());
            return ps;
        }, holder);
        return (Long) Objects.requireNonNull(holder.getKeys()).get("colorId");
    }

    @Override
    public void update(Color color) {
        Object[] params = new Object[]{color.getName(), color.getId()};
        Objects.requireNonNull(jdbcTemplate).update(updateColor, params);
    }

    @Override
    public void delete(Long id) {
        Objects.requireNonNull(jdbcTemplate).update(deleteColor, id);
    }
}
