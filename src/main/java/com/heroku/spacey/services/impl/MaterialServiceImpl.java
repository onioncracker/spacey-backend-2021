package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.MaterialDao;
import com.heroku.spacey.entity.Material;
import com.heroku.spacey.services.MaterialService;
import com.heroku.spacey.dto.material.MaterialDto;
import com.heroku.spacey.utils.convertors.BaseConvertor;
import com.heroku.spacey.utils.convertors.MaterialConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
public class MaterialServiceImpl implements MaterialService {
    private MaterialDao materialDao;
    private final BaseConvertor baseConvertor;
    private final MaterialConvertor materialConvertor;

    public MaterialServiceImpl(@Autowired MaterialDao materialDao,
                               @Autowired BaseConvertor baseConvertor,
                               @Autowired MaterialConvertor materialConvertor) {
        this.materialDao = materialDao;
        this.baseConvertor = baseConvertor;
        this.materialConvertor = materialConvertor;
    }

    @Override
    public List<MaterialDto> getAllMaterials() {
        List<Material> materials = materialDao.getAllMaterials();
        return baseConvertor.mapList(materials, MaterialDto.class);
    }

    @Override
    public MaterialDto getById(Long id) {
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
    public void updateMaterial(MaterialDto materialDto, Long id) {
        Material material = materialConvertor.adapt(materialDto);
        material.setId(id);
        materialDao.update(material);
    }

    @Override
    @Transactional
    public void deleteMaterial(Long id) {
        materialDao.delete(id);
    }
}
