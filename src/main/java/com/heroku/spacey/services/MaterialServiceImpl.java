package com.heroku.spacey.services;

import com.heroku.spacey.contracts.MaterialService;
import com.heroku.spacey.dao.common.UnitOfWorkImpl;
import com.heroku.spacey.dto.material.MaterialDto;
import com.heroku.spacey.mapper.MapperProfile;
import org.springframework.stereotype.Service;

@Service
public class MaterialServiceImpl implements MaterialService {
    private final UnitOfWorkImpl unitOfWorkImpl;

    public MaterialServiceImpl(UnitOfWorkImpl unitOfWorkImpl) {
        this.unitOfWorkImpl = unitOfWorkImpl;
    }

    @Override
    public MaterialDto getById(int id) {
        var material = unitOfWorkImpl.getMaterialDao().getById(id);
        if (material == null) {
            return null;
        }
        return MapperProfile.adapt(material);
    }

    @Override
    public void addMaterial(MaterialDto materialDto) {
        var material = MapperProfile.adapt(materialDto);
        unitOfWorkImpl.getMaterialDao().insert(material);
    }

    @Override
    public void updateMaterial(MaterialDto materialDto, int id) {
        var material = MapperProfile.adapt(materialDto);
        material.setId(id);
        unitOfWorkImpl.getMaterialDao().update(material);
    }

    @Override
    public void deleteMaterial(int id) {
        unitOfWorkImpl.getMaterialDao().delete(id);
    }
}
