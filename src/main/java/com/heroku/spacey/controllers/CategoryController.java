package com.heroku.spacey.controllers;

import com.heroku.spacey.services.CategoryService;
import com.heroku.spacey.dto.category.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

//TODO delete all try-catches
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;
    private static final String CATEGORY_NOT_FOUND = "category not found by id";

    public CategoryController(@Autowired CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable int id) {
        try {
            var category = categoryService.getById(id);
            if (category == null) {
                return new ResponseEntity(CATEGORY_NOT_FOUND, HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(category);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto) {
        try {
            categoryService.addCategory(categoryDto);
            return new ResponseEntity("added category successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<CategoryDto> editCategory(@RequestBody CategoryDto categoryDto, @PathVariable int id) {
        try {
            var category = categoryService.getById(id);
            if (category == null) {
                return new ResponseEntity(CATEGORY_NOT_FOUND, HttpStatus.NOT_FOUND);
            }
            categoryService.updateCategory(categoryDto, id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAuthority(T(com.heroku.spacey.utils.Role).PRODUCT_MANAGER)")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CategoryDto> deleteCategory(@PathVariable int id) {
        try {
            var category = categoryService.getById(id);
            if (category == null) {
                return new ResponseEntity(CATEGORY_NOT_FOUND, HttpStatus.NOT_FOUND);
            }
            categoryService.deleteCategory(category.getId());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
