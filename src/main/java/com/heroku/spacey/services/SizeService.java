package com.heroku.spacey.services;

import com.heroku.spacey.dto.size.AddSizeDto;
import com.heroku.spacey.dto.size.SizeDto;

import java.util.List;

public interface SizeService {
    List<SizeDto> getAllSizes();

    SizeDto getById(Long id);

    void addSize(AddSizeDto addSizeDto);

    void updateSize(SizeDto sizeDto, Long id);

    void deleteSize(Long id);
}
