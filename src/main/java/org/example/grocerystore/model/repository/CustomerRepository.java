// CustomerRepository.java
package org.example.grocerystore.model.repository;

import org.example.grocerystore.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Optional<Customer> findByUsername(String username);
    boolean existsByUsername(String username);
}
