package com.heroku.spacey.utils;

import com.heroku.spacey.dto.material.MaterialDto;
import com.heroku.spacey.entity.Material;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MaterialConvertor {
    private final ModelMapper mapper = new ModelMapper();

    public Material adapt(MaterialDto source) {
        mapper.typeMap(MaterialDto.class, Material.class);
        return mapper.map(source, Material.class);
    }

    public MaterialDto adapt(Material source) {
        mapper.typeMap(Material.class, MaterialDto.class);
        return mapper.map(source, MaterialDto.class);
    }
}
