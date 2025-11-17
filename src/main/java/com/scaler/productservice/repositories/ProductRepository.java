package com.scaler.productservice.repositories;

import com.scaler.productservice.models.Product;
import com.scaler.productservice.projections.ProductWithTitleAndPrice;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable; // CORRECT import java.util.List;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product>{
    @Override
    Optional<Product> findById(Long productId);

//    @Override
    Page<Product> findAll(Pageable pageable);

    @Override
    Product save(Product product);

    void deleteById(Long id);


    // HQL - Hibernate Query Language
    // We can write HQL queries based on the models instead of table from DB.
//    @Query("Select p.title as title, p.price as price from Product p where p.title = :title and p.price = :price") // for writing custom queries
//    List<ProductWithTitleAndPrice> getProductTitleAndPrice(String title, int price);

    // SQL query also called as Native Query
    @Query(value =  "select p.title, p.price from products p where p.title = :title and p.price = :price", nativeQuery = true)
    List<ProductWithTitleAndPrice> getProductTitleAndPriceSQL(String title, int price);
}
