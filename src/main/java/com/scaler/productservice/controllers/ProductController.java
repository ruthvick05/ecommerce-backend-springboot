package com.scaler.productservice.controllers;

import com.scaler.productservice.configurations.ProductServiceFactory;
import com.scaler.productservice.dtos.ProductFilterRequestDto;
import com.scaler.productservice.dtos.SearchRequest;
import com.scaler.productservice.exceptions.ProductNotFoundException;
import com.scaler.productservice.exceptions.ValidationException;
import com.scaler.productservice.models.Product;
import com.scaler.productservice.services.ProductService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductServiceFactory productServiceFactory) {
        this.productService = productServiceFactory.getService();
    }

    // http://localhost:8080/products/1
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) throws ProductNotFoundException {
//        ResponseEntity<Product> response = null;
//        try {
//            Product product = productService.getProductById(id);
//            response = new ResponseEntity(product, HttpStatus.OK);
//        }catch (Exception e){
//            response = new ResponseEntity(null, HttpStatus.BAD_REQUEST);
//        }
//        return response;
//        throw new RuntimeException("Hare Krishna");
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }


    //http://localhost:8080/products
//    @GetMapping()
//    public Page<Product> getAllProducts(@RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNumber) throws ProductNotFoundException {
//        return productService.getAllProducts(pageSize, pageNumber);
//    }
    @GetMapping
    public Page<Product> getAllProducts(
            @Valid ProductFilterRequestDto productFilterRequestDto,
            BindingResult bindingResult,
            @RequestParam int pageSize,
            @RequestParam int pageNumber,
            @RequestParam(defaultValue = "price") String sortBy,
            @RequestParam(defaultValue = "asc") String order
    ) {
        if (bindingResult.hasErrors()) {

            Map<String, String> errors = new HashMap<>();

            bindingResult.getFieldErrors()
                    .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));

            throw new ValidationException(errors);
        }

        return productService.getAllProducts(pageSize, pageNumber, sortBy, order, productFilterRequestDto);
    }

    @GetMapping("/search")
    public Page<Product> searchProducts(
            @Valid SearchRequest searchRequest,
            BindingResult bindingResult,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(defaultValue = "price") String sortBy,
            @RequestParam(defaultValue = "asc") String order
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();

            bindingResult.getFieldErrors()
                    .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));

            throw new ValidationException(errors);
        }
        return productService.searchProducts(searchRequest, page, size, sortBy, order);
    }

    @PostMapping()
    public Product createProduct(@RequestBody Product product){
        return productService.createProduct(product);
    }

    @PatchMapping("/{id}")
    public Product updateProduct(@PathVariable("id") Long id, @RequestBody Product product){
        return null;
    }

    @PutMapping("/{id}")
    public Product replaceProduct(@PathVariable("id") Long id, @RequestBody Product product){
        return productService.replaceProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long id){
        productService.deleteProduct(id);
    }

//    @ExceptionHandler
//    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException exceptionObj) {
//        return new ResponseEntity<>(exceptionObj.getMessage() + " from controller", HttpStatus.BAD_REQUEST);
//    }
}
