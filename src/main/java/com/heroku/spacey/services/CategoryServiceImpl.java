package com.heroku.spacey.services;

import com.heroku.spacey.dao.common.UnitOfWorkImpl;
import com.heroku.spacey.dao.category.CategoryDaoImpl;
import com.heroku.spacey.dto.category.CategoryDto;
import com.heroku.spacey.contracts.CategoryService;
import com.heroku.spacey.mapper.MapperProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final UnitOfWorkImpl unitOfWorkImpl;
    private final CategoryDaoImpl categoryDao;

    public CategoryServiceImpl(UnitOfWorkImpl unitOfWorkImpl, @Autowired CategoryDaoImpl categoryDao) {
        this.unitOfWorkImpl = unitOfWorkImpl;
        this.categoryDao = categoryDao;
    }

    @Override
    public CategoryDto getById(int id) {
        var category = categoryDao.getById(id);
        if (category == null) {
            return null;
        }
        return MapperProfile.adapt(category);
    }

    @Override
    public void addCategory(CategoryDto categoryDto) {
        var category = MapperProfile.adapt(categoryDto);
        unitOfWorkImpl.getCategoryDao().insert(category);
    }

    @Override
    public void updateCategory(CategoryDto categoryDto, int id) {
        var category = MapperProfile.adapt(categoryDto);
        category.setId(id);
        unitOfWorkImpl.getCategoryDao().update(category);
    }

    @Override
    public void deleteCategory(int id) {
        unitOfWorkImpl.getCategoryDao().delete(id);
    }
}
