package com.heroku.spacey.controllers;

import com.heroku.spacey.contracts.CategoryService;
import com.heroku.spacey.dto.category.CategoryDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/api/category/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable int id) {
        try {
            var category = categoryService.getById(id);
            if (category == null) {
                return new ResponseEntity("category not found by id", HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(category);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/api/category/add")
    public ResponseEntity addCategory(@RequestBody CategoryDto categoryDto) {
        try {
            categoryService.addCategory(categoryDto);
            return new ResponseEntity("added category successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/api/category/edit/{id}")
    public ResponseEntity<String> editCategory(@RequestBody CategoryDto categoryDto, @PathVariable int id) {
        try {
            var category = categoryService.getById(id);
            if (category == null) {
                return new ResponseEntity("category not found by id", HttpStatus.NOT_FOUND);
            }
            categoryService.updateCategory(categoryDto, id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/api/category/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable int id) {
        try {
            var category = categoryService.getById(id);
            if (category == null) {
                return new ResponseEntity("category not found by id", HttpStatus.NOT_FOUND);
            }
            categoryService.deleteCategory(category.getId());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
