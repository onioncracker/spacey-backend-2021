package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.ColorDao;
import com.heroku.spacey.dto.color.ColorDto;
import com.heroku.spacey.entity.Color;
import com.heroku.spacey.services.ColorService;
import com.heroku.spacey.utils.convertors.CommonConvertor;
import com.heroku.spacey.utils.convertors.ColorConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.List;

@Service
public class ColorServiceImpl implements ColorService {
    private ColorDao colorDao;
    private final CommonConvertor commonConvertor;
    private final ColorConvertor colorConvertor;

    public ColorServiceImpl(@Autowired ColorDao colorDao,
                            @Autowired CommonConvertor commonConvertor,
                            @Autowired ColorConvertor colorConvertor) {
        this.colorDao = colorDao;
        this.commonConvertor = commonConvertor;
        this.colorConvertor = colorConvertor;
    }

    @Override
    public List<ColorDto> getAllColors() {
        List<Color> colors = colorDao.getAllColors();
        return commonConvertor.mapList(colors, ColorDto.class);
    }

    @Override
    public ColorDto getById(Long id) {
        Color color = colorDao.getById(id);
        if (color == null) {
            throw new NotFoundException("Color not found");
        }
        return colorConvertor.adapt(color);
    }

    @Override
    @Transactional
    public void addColor(ColorDto colorDto) {
        Color color = colorConvertor.adapt(colorDto);
        colorDao.insert(color);
    }

    @Override
    @Transactional
    public void updateColor(ColorDto colorDto, Long id) {
        Color color = colorConvertor.adapt(colorDto);
        color.setId(id);
        colorDao.update(color);
    }

    @Override
    @Transactional
    public void deleteColor(Long id) {
        colorDao.delete(id);
    }
}
