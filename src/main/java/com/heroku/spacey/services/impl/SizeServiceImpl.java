package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.SizeDao;
import com.heroku.spacey.dto.size.AddSizeDto;
import com.heroku.spacey.dto.size.SizeDto;
import com.heroku.spacey.entity.Size;
import com.heroku.spacey.services.SizeService;
import com.heroku.spacey.utils.convertors.CommonConvertor;
import com.heroku.spacey.utils.convertors.SizeConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.List;

@Service
public class SizeServiceImpl implements SizeService {
    private SizeDao sizeDao;
    private final CommonConvertor commonConvertor;
    private final SizeConvertor sizeConvertor;

    public SizeServiceImpl(@Autowired SizeDao sizeDao,
                           @Autowired CommonConvertor commonConvertor,
                           @Autowired SizeConvertor sizeConvertor) {
        this.sizeDao = sizeDao;
        this.commonConvertor = commonConvertor;
        this.sizeConvertor = sizeConvertor;
    }

    @Override
    public List<SizeDto> getAllSizes() {
        List<Size> sizes = sizeDao.getAllSizes();
        return commonConvertor.mapList(sizes, SizeDto.class);
    }

    @Override
    public SizeDto getById(Long id) {
        Size size = sizeDao.getById(id);
        if (size == null) {
            throw new NotFoundException("Size not found");
        }
        return sizeConvertor.adapt(size);
    }

    @Override
    @Transactional
    public void addSize(AddSizeDto addSizeDto) {
        Size size = sizeConvertor.adapt(addSizeDto);
        sizeDao.insert(size);
    }

    @Override
    @Transactional
    public void updateSize(SizeDto sizeDto, Long id) {
        Size size = sizeConvertor.adapt(sizeDto);
        size.setId(id);
        sizeDao.update(size);
    }

    @Override
    @Transactional
    public void deleteSize(Long id) {
        sizeDao.delete(id);
    }
}
