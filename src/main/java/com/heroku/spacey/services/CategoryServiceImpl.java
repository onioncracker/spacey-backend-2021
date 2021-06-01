package com.heroku.spacey.services;

import com.heroku.spacey.dao.UnitOfWork;
import com.heroku.spacey.dto.category.CategoryDto;
import com.heroku.spacey.contracts.CategoryService;
import com.heroku.spacey.mapper.MapperProfile;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final UnitOfWork unitOfWork;

    public CategoryServiceImpl(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public CategoryDto getById(int id) {
        var category = unitOfWork.getCategoryDao().getById(id);
        if (category == null) {
            return null;
        }
        return MapperProfile.adapt(category);
    }

    @Override
    public void addCategory(CategoryDto categoryDto) {
        var category = MapperProfile.adapt(categoryDto);
        unitOfWork.getCategoryDao().insert(category);
    }

    @Override
    public void updateCategory(CategoryDto categoryDto, int id) {
        var category = MapperProfile.adapt(categoryDto);
        category.setId(id);
        unitOfWork.getCategoryDao().update(category);
    }

    @Override
    public void deleteCategory(int id) {
        unitOfWork.getCategoryDao().delete(id);
    }
}
