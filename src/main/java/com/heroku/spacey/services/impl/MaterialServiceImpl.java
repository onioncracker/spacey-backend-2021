package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.MaterialDao;
import com.heroku.spacey.entity.Material;
import com.heroku.spacey.services.MaterialService;
import com.heroku.spacey.dto.material.MaterialDto;
import com.heroku.spacey.utils.MaterialConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

@Service
public class MaterialServiceImpl implements MaterialService {
    private MaterialDao materialDao;
    private final MaterialConvertor materialConvertor;

    public MaterialServiceImpl(@Autowired MaterialDao materialDao,
                               @Autowired MaterialConvertor materialConvertor) {
        this.materialDao = materialDao;
        this.materialConvertor = materialConvertor;
    }

    @Override
    public MaterialDto getById(int id) {
        Material material = materialDao.getById(id);
        if (material == null) {
            throw new NotFoundException("Material not found");
        }
        return materialConvertor.adapt(material);
    }

    @Override
    @Transactional
    public void addMaterial(MaterialDto materialDto) {
        Material material = materialConvertor.adapt(materialDto);
        materialDao.insert(material);
    }

    @Override
    @Transactional
    public void updateMaterial(MaterialDto materialDto, int id) {
        Material material = materialConvertor.adapt(materialDto);
        material.setId(id);
        materialDao.update(material);
    }

    @Override
    @Transactional
    public void deleteMaterial(int id) {
        materialDao.delete(id);
    }
}
