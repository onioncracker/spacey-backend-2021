package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.CategoryDao;
import com.heroku.spacey.dto.category.CategoryDto;
import com.heroku.spacey.services.CategoryService;
import com.heroku.spacey.utils.convertors.CategoryConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao;
    private final CategoryConvertor categoryConvertor;

    public CategoryServiceImpl(@Autowired CategoryDao categoryDao,
                               @Autowired CategoryConvertor categoryConvertor) {
        this.categoryDao = categoryDao;
        this.categoryConvertor = categoryConvertor;
    }

    @Override
    @Transactional
    public CategoryDto getById(int id) {
        var category = categoryDao.getById(id);
        if (category == null) {
            return null;
        }
        return categoryConvertor.adapt(category);
    }

    @Override
    @Transactional
    public void addCategory(CategoryDto categoryDto) {
        var category = categoryConvertor.adapt(categoryDto);
        categoryDao.insert(category);
    }

    @Override
    @Transactional
    public void updateCategory(CategoryDto categoryDto, int id) {
        var category = categoryConvertor.adapt(categoryDto);
        category.setId(id);
        categoryDao.update(category);
    }

    @Override
    @Transactional
    public void deleteCategory(int id) {
        categoryDao.delete(id);
    }
}
