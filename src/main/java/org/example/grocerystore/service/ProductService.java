// ProductService.java
package org.example.grocerystore.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.grocerystore.model.entity.Product;
import org.example.grocerystore.model.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository repo;
    public ProductService(ProductRepository r){ this.repo=r; }
    public void create(Product p){ repo.save(p); }
    public void update(Product p){ repo.save(p); }
    public Optional<Product> findById(Long id){ return repo.findById(id); }
    public ObservableList<Product> getAllProducts(){
        return FXCollections.observableArrayList(repo.findAll());
    }
    public void delete(Long id){ repo.deleteById(id); }
}
