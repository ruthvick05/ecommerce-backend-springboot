package com.scaler.productservice.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
// lombok library provides us getter, setter methods automatically
@Getter
@Setter
@Entity(name = "products")
public class Product extends BaseModel {
    private String title;
    private String description;
    private int price;
    private String imageUrl;

    @ManyToOne(cascade = CascadeType.PERSIST) // defines cardinality between products table and categories table
    private Category category;
}
