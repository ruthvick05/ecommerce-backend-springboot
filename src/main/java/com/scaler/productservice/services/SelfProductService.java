package com.scaler.productservice.services;

import com.scaler.productservice.dtos.ProductFilterRequestDto;
import com.scaler.productservice.models.Category;
import com.scaler.productservice.repositories.CategoryRepository;
import com.scaler.productservice.repositories.ProductRepository;
import com.scaler.productservice.exceptions.ProductNotFoundException;
import com.scaler.productservice.models.Product;
import com.scaler.productservice.specifications.ProductSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("selfProductService") // parenthesis string is the name of the bean of this service class that springBoot will create
public class SelfProductService implements ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public SelfProductService(ProductRepository productRepository,
                              CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getProductById(Long productId) throws ProductNotFoundException {
        Optional<Product> productOptional = productRepository.findById(productId);
        if(productOptional.isEmpty()) throw new ProductNotFoundException("Product of id: " + productId + " not found");
        return productOptional.get();
    }

    @Override
    public Page<Product> getAllProducts(int pageSize, int pageNumber, String sortBy, String order, ProductFilterRequestDto productFilterRequestDto) {
        // Allowed fields
        List<String> allowedFields = List.of("price", "title", "id");

        // Validate
        if (!allowedFields.contains(sortBy)) {
            throw new IllegalArgumentException("Invalid sort field: " + sortBy);
        }

        // sorting logic
        Sort sort = order.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Specification<Product> spec = ProductSpecification.withFilters(productFilterRequestDto);

        return productRepository.findAll(spec, pageable);

    }

    @Override
    public Page<Product> searchProducts(com.scaler.productservice.dtos.SearchRequest request, int page, int size, String sortBy, String order) {
        // Allowed fields
        List<String> allowedFields = List.of("price", "title", "id");

        // Validate
        if (!allowedFields.contains(sortBy)) {
            throw new IllegalArgumentException("Invalid sort field: " + sortBy);
        }

        // sorting logic
        Sort sort = order.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return productRepository.searchProducts(request.getSearchTerm(), pageable);
    }

    @Override
    public Product createProduct(Product product) {
        Category category = product.getCategory();
        Optional<Category> optionalCategory = categoryRepository.findByName(category.getName());
//         if category does not exist in our DB then first create new category and add it to DB.
        if(optionalCategory.isEmpty()){
            // save category
            category = categoryRepository.save(category); // why do we need to update the category in this line?
            // ans: bcoz input category does not have id so we are updating our reference to new category obj created by save method.

        }else{
            category = optionalCategory.get();
        }
        product.setCategory(category);
        return productRepository.save(product);
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        Category category = product.getCategory();
        if(optionalProduct.isEmpty()) throw new ProductNotFoundException("Product of id: " + id + " not found");
        if(category.getId() == null){
            // add it to DB
            category = categoryRepository.save(category);
        }

        Product oldProduct = optionalProduct.get();

        oldProduct.setPrice(product.getPrice());
        oldProduct.setCategory(category);
        oldProduct.setDescription(product.getDescription());
        oldProduct.setTitle(product.getTitle());
        oldProduct.setImageUrl(product.getImageUrl());

        return productRepository.save(oldProduct);
    }

    @Override
    public void deleteProduct(Long id){
//        if(!productRepository.existsById(id)){
//            throw new ProductNotFoundException("Product of id: " + id + " not found");
//        }
        productRepository.deleteById(id);
    }


}
