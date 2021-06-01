package com.heroku.spacey.services;

import com.heroku.spacey.contracts.MaterialService;
import com.heroku.spacey.dao.UnitOfWork;
import com.heroku.spacey.dto.material.MaterialDto;
import com.heroku.spacey.mapper.MapperProfile;
import org.springframework.stereotype.Service;

@Service
public class MaterialServiceImpl implements MaterialService {
    private final UnitOfWork unitOfWork;

    public MaterialServiceImpl(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public MaterialDto getById(int id) {
        var material = unitOfWork.getMaterialDao().getById(id);
        if (material == null) {
            return null;
        }
        return MapperProfile.adapt(material);
    }

    @Override
    public void addMaterial(MaterialDto materialDto) {
        var material = MapperProfile.adapt(materialDto);
        unitOfWork.getMaterialDao().insert(material);
    }

    @Override
    public void updateMaterial(MaterialDto materialDto) {

    }

    @Override
    public void deleteMaterial(int id) {
        var isFound = unitOfWork.getMaterialDao().isExist(id);
        if (!isFound) {
            return; //TODO throw exception to frontend
        }
        unitOfWork.getMaterialDao().delete(id);
    }
}
