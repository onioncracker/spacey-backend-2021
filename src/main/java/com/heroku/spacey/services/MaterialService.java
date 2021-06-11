package com.heroku.spacey.services;

import com.heroku.spacey.dto.material.MaterialDto;

import java.util.List;

public interface MaterialService {
    List<MaterialDto> getAllMaterials();

    MaterialDto getById(Long id);

    void addMaterial(MaterialDto materialDto);

    void updateMaterial(MaterialDto materialDto, Long id);

    void deleteMaterial(Long id);
}
