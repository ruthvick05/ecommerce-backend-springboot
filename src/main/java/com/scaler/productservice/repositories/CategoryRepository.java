package com.scaler.productservice.repositories;

import com.scaler.productservice.models.Category;

import com.scaler.productservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.scaler.productservice.models.Category;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

    Optional<Category> findByName(String name);

    @Override
    Category save(Category category);

    void deleteById(long id);

    Optional<Category> findById(long id);

//    List<Product> getProducts();
}
