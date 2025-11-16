package com.scaler.productservice.configurations;

import com.scaler.productservice.services.FakeStoreProductService;
import com.scaler.productservice.services.ProductService;
import com.scaler.productservice.services.SelfProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceFactory {
    @Value("${product.service.type}")
    private String activeService;
    private FakeStoreProductService fakeStoreProductService;
    private SelfProductService selfProductService;
    public ProductServiceFactory(FakeStoreProductService fakeStoreProductService,
                                 SelfProductService selfProductService) {
        this.fakeStoreProductService = fakeStoreProductService;
        this.selfProductService = selfProductService;
    }

    public ProductService getService() {
        if (activeService.equalsIgnoreCase("self")) {
            return selfProductService;
        }
        return fakeStoreProductService;
    }
}
