package com.scaler.productservice.services;

import com.scaler.productservice.dtos.FakeStoreProductDTO;
import com.scaler.productservice.exceptions.ProductNotFoundException;
import com.scaler.productservice.models.Category;
import com.scaler.productservice.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService {
    // This service implementation uses Fake Store to fetch products

    //  ✔ Spring checks the constructor and sees that RestTemplate is needed.
    //  ✔ Spring looks for an existing RestTemplate Bean in the Application Context.
    //  ✔ If found, Spring passes the existing Bean instead of creating a new one.
    //  ✔ This ensures the same RestTemplate instance is used everywhere (Singleton scope).

    private RestTemplate restTemplate;
    public FakeStoreProductService(RestTemplate restTemplate) { // constructor
        this.restTemplate = restTemplate;
    }

    //Here’s what happens:
    //1️⃣ Spring checks what arguments the constructor needs (in this case, RestTemplate).
    //2️⃣ Spring looks in the Application Context (which stores all Beans).
    //3️⃣ If a RestTemplate Bean already exists (from @Bean in AppConfig), Spring passes that Bean into the constructor.
    //   ✔ This is called "Dependency Injection" – Spring is injecting RestTemplate into FakeStoreProductService.
    //4️⃣ If no RestTemplate Bean is found, Spring throws an error (No qualifying Bean found).

    private Product convertFakeStoreProductDTOToProduct(FakeStoreProductDTO fakeStoreProductDTO) {
        Product product = new Product();
        product.setId(fakeStoreProductDTO.getId());
        product.setTitle(fakeStoreProductDTO.getTitle());
        product.setImageUrl(fakeStoreProductDTO.getImage());
        product.setDescription(fakeStoreProductDTO.getDescription());
        product.setPrice(fakeStoreProductDTO.getPrice());
        Category category = new Category();
        category.setName(fakeStoreProductDTO.getCategory());
        product.setCategory(category);

        return product;
    }

    @Override
    public Product getProductById(Long productId) throws ProductNotFoundException {
        // Make API call to fake store and get product with given id

        //The getForObject() method in RestTemplate is used to send an HTTP GET request to a given URL
        // and retrieve the response as an object.
        FakeStoreProductDTO fakeStoreProductDTO =
                restTemplate.getForObject("https://fakestoreapi.com/products/{id}", FakeStoreProductDTO.class, productId);
        // convert fakeStoreProductDTO obj to our product obj
        if(fakeStoreProductDTO == null) {
            throw new ProductNotFoundException("Product with id: " + productId + " does not exist.");
        }
        return convertFakeStoreProductDTOToProduct(fakeStoreProductDTO);
//        throw new RuntimeException("Jai Gaura Nitai");
    }

//    @Override
//    public Page<Product> getAllProducts(int pageSize, int pageNumber) {
//        // wrong implementation:
//        //      we want product objs in list
//        //      This will return a raw List with elements as LinkedHashMap, not FakeStoreProductDTO objects.
//        //      It doesn’t know the actual type due to type erasure
//        //return restTemplate.getForObject("https://fakestoreapi.com/products", List.class);
//
//        // wrong implementation 2:
//        //      List<FakeStoreProductDTO>.class doesn’t exist due to Type Erasure.
////        List<FakeStoreProductDTO> fakeStoreProductDTOs =
////                restTemplate.getForObject("https://fakestoreapi.com/products", List<FakeStoreProductDTO>.class);
//
//        FakeStoreProductDTO[] fakeStoreProductDTOS =
//                restTemplate.getForObject("https://fakestoreapi.com/products", FakeStoreProductDTO[].class);
//        // type eraser works on only collection objects like List<T> but arr[] is a data type in java so no typre erasure here
//        List<Product> products = new ArrayList<>();
//        for(FakeStoreProductDTO fakeStoreProductDTO : fakeStoreProductDTOS) {
//            products.add(convertFakeStoreProductDTOToProduct(fakeStoreProductDTO));
//        }
//        return new PageImpl<>(products);
//    }

    @Override
    public Page<Product> getAllProducts(int pageSize, int pageNumber, String sortBy, String order) {

        // Step 1: Fetch raw DTOs from FakeStore API
        FakeStoreProductDTO[] fakeStoreProductDTOS =
                restTemplate.getForObject("https://fakestoreapi.com/products", FakeStoreProductDTO[].class);

        if (fakeStoreProductDTOS == null) {
            return Page.empty();
        }

        // Step 2: Convert DTOs → Product model (your internal format)
        List<Product> products = new ArrayList<>();
        for (FakeStoreProductDTO dto : fakeStoreProductDTOS) {
            products.add(convertFakeStoreProductDTOToProduct(dto));
        }

        // Step 3: Allowed sorting fields (same as DB service for consistency)
        List<String> allowedFields = List.of("id", "title", "price");

        if (!allowedFields.contains(sortBy)) {
            throw new IllegalArgumentException("Invalid sort field: " + sortBy);
        }

        // Step 4: Sorting logic using Comparator
        Comparator<Product> comparator;

        switch (sortBy) {
            case "price":
                comparator = Comparator.comparing(Product::getPrice);
                break;
            case "title":
                comparator = Comparator.comparing(Product::getTitle);
                break;
            default:
                comparator = Comparator.comparing(Product::getId);
        }

        if (order.equalsIgnoreCase("desc")) {
            comparator = comparator.reversed();
        }

        products.sort(comparator);

        // Step 5: Manual pagination
        int total = products.size();
        int start = pageNumber * pageSize;
        int end = Math.min(start + pageSize, total);

        if (start >= total) {
            return Page.empty();
        }

        List<Product> pageContent = products.subList(start, end);

        // Step 6: Return Spring Page with pagination info
        return new PageImpl<>(pageContent, PageRequest.of(pageNumber, pageSize), total);
    }


    @Override
    public Product createProduct(Product product) {
        return null;
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        return null;
    }

    @Override
    public void deleteProduct(Long id) {
    }
}