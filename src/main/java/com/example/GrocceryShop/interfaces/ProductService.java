package com.example.GrocceryShop.interfaces;

import com.example.GrocceryShop.model.Product;
import com.example.GrocceryShop.service.AppService;

import java.util.Optional;

public interface ProductService extends AppService<Product> {
    Optional<Product> findByName(String name);
    void updateProduct(Long id, Product updatedProduct);
}
