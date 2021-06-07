package com.heroku.spacey.dao;

import com.heroku.spacey.entity.Material;

public interface MaterialDao {
    Material getById(Long id);

    Long insert(Material material);

    void update(Material material);

    void delete(Long id);
}
