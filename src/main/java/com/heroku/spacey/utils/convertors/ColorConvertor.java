package com.heroku.spacey.utils.convertors;

import com.heroku.spacey.dto.color.ColorDto;
import com.heroku.spacey.entity.Color;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ColorConvertor {
    private final ModelMapper mapper = new ModelMapper();

    public Color adapt(ColorDto source) {
        mapper.typeMap(ColorDto.class, Color.class);
        return mapper.map(source, Color.class);
    }

    public ColorDto adapt(Color source) {
        mapper.typeMap(Color.class, ColorDto.class);
        return mapper.map(source, ColorDto.class);
    }
}
