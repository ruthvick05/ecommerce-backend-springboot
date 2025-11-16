package com.scaler.productservice.projections;


// projections are used when you want only few attributes of a model frojm the DB
// for this just create an interface and declare getter methods for the attributes that you need
public interface ProductWithTitleAndPrice {
    String getTitle();
    int getPrice();
}
