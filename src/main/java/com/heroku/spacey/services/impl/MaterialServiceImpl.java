package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.MaterialDao;
import com.heroku.spacey.services.MaterialService;
import com.heroku.spacey.dto.material.MaterialDto;
import com.heroku.spacey.utils.MaterialConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaterialServiceImpl implements MaterialService {
    private MaterialDao materialDao;
    private final MaterialConvertor materialConvertor;

    public MaterialServiceImpl(@Autowired MaterialDao materialDao, @Autowired MaterialConvertor materialConvertor) {
        this.materialDao = materialDao;
        this.materialConvertor = materialConvertor;
    }

    @Override
    public MaterialDto getById(int id) {
        var material = materialDao.getById(id);
        if (material == null) {
            return null;
        }
        return materialConvertor.adapt(material);
    }

    @Override
    public void addMaterial(MaterialDto materialDto) {
        var material = materialConvertor.adapt(materialDto);
        materialDao.insert(material);
    }

    @Override
    public void updateMaterial(MaterialDto materialDto, int id) {
        var material = materialConvertor.adapt(materialDto);
        material.setId(id);
        materialDao.update(material);
    }

    @Override
    public void deleteMaterial(int id) {
        materialDao.delete(id);
    }
}
