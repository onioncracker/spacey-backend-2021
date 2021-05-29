package com.heroku.spacey.services;

import com.heroku.spacey.dao.UnitOfWork;
import com.heroku.spacey.dto.category.CategoryDto;
import com.heroku.spacey.entity.Category;
import com.heroku.spacey.contracts.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final UnitOfWork unitOfWork;

    public CategoryServiceImpl(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public void addCategory(CategoryDto categoryDto) {
        var category = new Category();
        category.setName(categoryDto.getName());
        unitOfWork.getCategoryDao().insert(category);
    }
}
