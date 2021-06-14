package com.heroku.spacey.dao;

import com.heroku.spacey.entity.Material;

import java.util.List;

public interface MaterialDao {
    List<Material> getAllMaterials();

    Material getById(Long id);

    Long insert(Material material);

    void update(Material material);

    void delete(Long id);
}
