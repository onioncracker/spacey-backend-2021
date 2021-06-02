package com.heroku.spacey.contracts;

import com.heroku.spacey.dto.material.MaterialDto;

public interface MaterialService {
    MaterialDto getById(int id);

    void addMaterial(MaterialDto materialDto);

    void updateMaterial(MaterialDto materialDto, int id);

    void deleteMaterial(int id);
}
