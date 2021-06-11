package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.CategoryDao;
import com.heroku.spacey.dto.category.CategoryDto;
import com.heroku.spacey.entity.Category;
import com.heroku.spacey.services.CategoryService;
import com.heroku.spacey.utils.convertors.BaseConvertor;
import com.heroku.spacey.utils.convertors.CategoryConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao;
    private final BaseConvertor baseConvertor;
    private final CategoryConvertor categoryConvertor;

    public CategoryServiceImpl(@Autowired CategoryDao categoryDao,
                               @Autowired BaseConvertor baseConvertor,
                               @Autowired CategoryConvertor categoryConvertor) {
        this.categoryDao = categoryDao;
        this.baseConvertor = baseConvertor;
        this.categoryConvertor = categoryConvertor;
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryDao.getAllCategories();
        return baseConvertor.mapList(categories, CategoryDto.class);
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
