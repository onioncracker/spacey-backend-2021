package com.heroku.spacey.services;

import com.heroku.spacey.dao.common.UnitOfWork;
import com.heroku.spacey.dto.category.CategoryDto;
import com.heroku.spacey.contracts.CategoryService;
import com.heroku.spacey.mapper.CategoryConvertor;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final UnitOfWork unitOfWork;
    private final CategoryConvertor categoryConvertor;

    public CategoryServiceImpl(UnitOfWork unitOfWork, CategoryConvertor categoryConvertor) {
        this.unitOfWork = unitOfWork;
        this.categoryConvertor = categoryConvertor;
    }

    @Override
    public CategoryDto getById(int id) {
        var category = unitOfWork.getCategoryDao().getById(id);
        if (category == null) {
            return null;
        }
        return categoryConvertor.adapt(category);
    }

    @Override
    public void addCategory(CategoryDto categoryDto) {
        var category = categoryConvertor.adapt(categoryDto);
        unitOfWork.getCategoryDao().insert(category);
    }

    @Override
    public void updateCategory(CategoryDto categoryDto, int id) {
        var category = categoryConvertor.adapt(categoryDto);
        category.setId(id);
        unitOfWork.getCategoryDao().update(category);
    }

    @Override
    public void deleteCategory(int id) {
        unitOfWork.getCategoryDao().delete(id);
    }
}
