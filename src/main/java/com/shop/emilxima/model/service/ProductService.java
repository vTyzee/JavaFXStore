package com.shop.emilxima.model.service;

import com.shop.emilxima.model.entity.Product;
import com.shop.emilxima.model.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepo;

    public ProductService(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    public void addProduct(String name, double price, int quantity) {
        Product p = new Product(name, price, quantity);
        productRepo.save(p);
    }

    // analg editProduct, findAll, etc.
}
