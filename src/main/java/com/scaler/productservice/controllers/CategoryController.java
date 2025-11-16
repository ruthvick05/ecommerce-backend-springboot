package com.scaler.productservice.controllers;

import com.scaler.productservice.services.CategoryService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Getter
@Setter
@RestController
@RequestMapping("/categories")
public class CategoryController {
    private CategoryService categoryService;
    CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable("id") Long categoryId){
        categoryService.deleteCategory(categoryId);
    }
}
