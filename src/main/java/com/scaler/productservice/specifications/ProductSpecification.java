package com.scaler.productservice.specifications;

import com.scaler.productservice.dtos.ProductFilterRequestDto;
import com.scaler.productservice.models.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;


public class ProductSpecification {

    public static Specification<Product> withFilters(ProductFilterRequestDto filter) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filter by category
            if (filter.getCategory() != null && !filter.getCategory().isBlank()) {
                predicates.add(cb.equal(root.get("category").get("name"), filter.getCategory()));
            }

            // Filter by brand
            if (filter.getBrand() != null && !filter.getBrand().isBlank()) {
                predicates.add(cb.equal(root.get("brand"), filter.getBrand()));
            }

            // min price
            if (filter.getMinPrice() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), filter.getMinPrice()));
            }

            // max price
            if (filter.getMaxPrice() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), filter.getMaxPrice()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
