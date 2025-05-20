// ProductRepository.java
package org.example.grocerystore.model.repository;

import org.example.grocerystore.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {}
