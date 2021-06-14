package com.heroku.spacey.controllers;

import com.heroku.spacey.services.CategoryService;
import com.heroku.spacey.dto.category.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/all")
    public List<CategoryDto> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public CategoryDto getCategory(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @PostMapping("/add")
    public HttpStatus addCategory(@RequestBody CategoryDto categoryDto) {
        categoryService.addCategory(categoryDto);
        return HttpStatus.CREATED;
    }

    @PutMapping("/edit/{id}")
    public HttpStatus editCategory(@RequestBody CategoryDto categoryDto,
                                   @PathVariable Long id) {
        CategoryDto category = categoryService.getById(id);
        categoryService.updateCategory(categoryDto, category.getId());
        return HttpStatus.OK;
    }

    @DeleteMapping("/delete/{id}")
    public HttpStatus deleteCategory(@PathVariable Long id) {
        CategoryDto category = categoryService.getById(id);
        categoryService.deleteCategory(category.getId());
        return HttpStatus.ACCEPTED;
    }
}
