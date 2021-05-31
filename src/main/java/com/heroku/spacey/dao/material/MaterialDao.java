package com.heroku.spacey.dao.material;

import com.heroku.spacey.entity.Material;

public interface MaterialDao {
    Material getById(int id);

    int getByName(String name);

    int insert(Material material);

    void update(Material material);

    void delete(int id);
}
