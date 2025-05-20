// SupplierRepository.java
package org.example.grocerystore.model.repository;

import org.example.grocerystore.model.entity.Supplier;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;

public interface SupplierRepository extends JpaRepository<Supplier,Long> {
    @Modifying @Transactional
    @Query(value="DELETE FROM product_suppliers WHERE supplier_id = :id", nativeQuery=true)
    void deleteProductSupplierLinks(@Param("id")Long id);
}
