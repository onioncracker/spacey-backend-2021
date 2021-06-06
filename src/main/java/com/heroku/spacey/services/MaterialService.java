package com.heroku.spacey.services;

import com.heroku.spacey.dto.material.MaterialDto;

public interface MaterialService {
    MaterialDto getById(Long id);

    void addMaterial(MaterialDto materialDto);

    void updateMaterial(MaterialDto materialDto, Long id);

    void deleteMaterial(Long id);
}
