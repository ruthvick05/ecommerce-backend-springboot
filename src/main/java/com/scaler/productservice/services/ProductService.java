package com.scaler.productservice.services;

import com.scaler.productservice.dtos.ProductFilterRequestDto;
import com.scaler.productservice.exceptions.ProductNotFoundException;
import com.scaler.productservice.models.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    public Product getProductById(Long productId) throws ProductNotFoundException;
    public Page<Product> getAllProducts(int pageSize, int pageNumber, String sortBy, String order, ProductFilterRequestDto productFilterRequestDto);
    public Product createProduct(Product product);
    public Product replaceProduct(Long id, Product product);
    void deleteProduct(Long id);
}
