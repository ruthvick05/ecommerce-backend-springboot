package com.scaler.productservice.services;

import com.scaler.productservice.repositories.CategoryRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    public void deleteCategory(Long categoryId){
        categoryRepository.deleteById(categoryId);
    }
}
