package com.heroku.spacey.dao;

import com.heroku.spacey.entity.Material;

public interface MaterialDao {
    Material getById(int id);

    int insert(Material material);

    void update(Material material);

    void delete(int id);
}
