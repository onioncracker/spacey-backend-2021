package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.CategoryDao;
import com.heroku.spacey.dto.category.CategoryDto;
import com.heroku.spacey.entity.Category;
import com.heroku.spacey.services.CategoryService;
import com.heroku.spacey.utils.CategoryConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

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
    public CategoryDto getById(Long id) {
        Category category = categoryDao.getById(id);
        if (category == null) {
            throw new NotFoundException("Category not found");
        }
        return categoryConvertor.adapt(category);
    }

    @Override
    @Transactional
    public void addCategory(CategoryDto categoryDto) {
        Category category = categoryConvertor.adapt(categoryDto);
        categoryDao.insert(category);
    }

    @Override
    @Transactional
    public void updateCategory(CategoryDto categoryDto, Long id) {
        Category category = categoryConvertor.adapt(categoryDto);
        category.setId(id);
        categoryDao.update(category);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        categoryDao.delete(id);
    }
}
