package com.scaler.productservice.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductFilterRequestDto {
    @DecimalMin(value = "0.0", inclusive = false, message = "Minimum price must be greater than 0")
    private Double minPrice;

    @DecimalMin(value = "0.0", inclusive = false, message = "Maximum price must be greater than 0")
    private Double maxPrice;

    @Size(max = 50, message = "Brand name must not exceed 50 characters")
    private String brand;

    @Size(max = 50, message = "Category name must not exceed 50 characters")
    private String category;
}
