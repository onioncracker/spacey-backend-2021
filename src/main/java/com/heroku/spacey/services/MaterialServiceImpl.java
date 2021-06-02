package com.heroku.spacey.services;

import com.heroku.spacey.contracts.MaterialService;
import com.heroku.spacey.dao.common.UnitOfWork;
import com.heroku.spacey.dto.material.MaterialDto;
import com.heroku.spacey.mapper.MaterialConvertor;
import org.springframework.stereotype.Service;

@Service
public class MaterialServiceImpl implements MaterialService {
    private final UnitOfWork unitOfWork;
    private final MaterialConvertor materialConvertor;

    public MaterialServiceImpl(UnitOfWork unitOfWork, MaterialConvertor materialConvertor) {
        this.unitOfWork = unitOfWork;
        this.materialConvertor = materialConvertor;
    }

    @Override
    public MaterialDto getById(int id) {
        var material = unitOfWork.getMaterialDao().getById(id);
        if (material == null) {
            return null;
        }
        return materialConvertor.adapt(material);
    }

    @Override
    public void addMaterial(MaterialDto materialDto) {
        var material = materialConvertor.adapt(materialDto);
        unitOfWork.getMaterialDao().insert(material);
    }

    @Override
    public void updateMaterial(MaterialDto materialDto, int id) {
        var material = materialConvertor.adapt(materialDto);
        material.setId(id);
        unitOfWork.getMaterialDao().update(material);
    }

    @Override
    public void deleteMaterial(int id) {
        unitOfWork.getMaterialDao().delete(id);
    }
}
