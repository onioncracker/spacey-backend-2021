package com.heroku.spacey.dao;

import com.heroku.spacey.entity.Size;

import java.util.List;

public interface SizeDao {
    List<Size> getAllSizes();

    Size getById(Long id);

    Long insert(Size size);

    void update(Size size);

    void delete(Long id);
}
