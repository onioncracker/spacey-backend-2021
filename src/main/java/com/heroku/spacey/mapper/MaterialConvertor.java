package com.heroku.spacey.mapper;

import com.heroku.spacey.dto.material.MaterialDto;
import com.heroku.spacey.entity.Material;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MaterialConvertor {
    private final ModelMapper mapper;

    public MaterialConvertor(@Autowired ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Material adapt(MaterialDto source) {
        mapper.typeMap(MaterialDto.class, Material.class);
        return mapper.map(source, Material.class);
    }

    public MaterialDto adapt(Material source) {
        mapper.typeMap(Material.class, MaterialDto.class);
        return mapper.map(source, MaterialDto.class);
    }
}
