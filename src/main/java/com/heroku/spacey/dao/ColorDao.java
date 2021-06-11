package com.heroku.spacey.dao;

import com.heroku.spacey.entity.Color;

import java.util.List;

public interface ColorDao {
    List<Color> getAllColors();

    Color getById(Long id);

    Long insert(Color color);

    void update(Color color);

    void delete(Long id);
}
