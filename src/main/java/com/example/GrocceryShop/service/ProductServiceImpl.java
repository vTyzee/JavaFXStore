package com.example.GrocceryShop.service;

import com.example.GrocceryShop.interfaces.ProductService;
import com.example.GrocceryShop.model.Product;
import com.example.GrocceryShop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Продукт с ID " + id + " не найден!");
        }
        productRepository.deleteById(id);
    }

    @Override
    public Optional<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public void updateProduct(Long id, Product updatedProduct) {
        Product product = getById(id).orElseThrow(() -> new RuntimeException("Продукт не найден!"));

        product.setName(updatedProduct.getName());
        product.setCategory(updatedProduct.getCategory());
        product.setPrice(updatedProduct.getPrice());
        productRepository.save(product);
    }
}
