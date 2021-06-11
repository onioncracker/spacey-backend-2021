package com.heroku.spacey.services;

import com.heroku.spacey.dto.color.ColorDto;

import java.util.List;

public interface ColorService {
    List<ColorDto> getAllColors();

    ColorDto getById(Long id);

    void addColor(ColorDto colorDto);

    void updateColor(ColorDto colorDto, Long id);

    void deleteColor(Long id);
}
