package com.heroku.spacey.utils.convertors;

import com.heroku.spacey.dto.size.AddSizeDto;
import com.heroku.spacey.dto.size.SizeDto;
import com.heroku.spacey.entity.Size;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SizeConvertor {
    private final ModelMapper mapper = new ModelMapper();

    public Size adapt(SizeDto source) {
        mapper.typeMap(SizeDto.class, Size.class);
        return mapper.map(source, Size.class);
    }

    public SizeDto adapt(Size source) {
        mapper.typeMap(Size.class, SizeDto.class);
        return mapper.map(source, SizeDto.class);
    }

    public Size adapt(AddSizeDto source) {
        mapper.typeMap(AddSizeDto.class, Size.class);
        return mapper.map(source, Size.class);
    }
}
