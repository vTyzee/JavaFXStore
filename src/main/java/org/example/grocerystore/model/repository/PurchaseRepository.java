// PurchaseRepository.java
package org.example.grocerystore.model.repository;

import org.example.grocerystore.model.entity.Purchase;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase,Long> {

    @Query("select sum(p.totalPrice) from Purchase p where p.purchaseDate between :start and :end")
    Double getIncomeBetween(@Param("start") LocalDateTime s, @Param("end") LocalDateTime e);

    @Query("select p.product, sum(p.quantity) from Purchase p " +
            "where p.purchaseDate between :start and :end " +
            "group by p.product order by sum(p.quantity) desc")
    List<Object[]> getTopProductBetween(@Param("start")LocalDateTime s,@Param("end")LocalDateTime e);

    @Query("select p.product, sum(p.quantity) from Purchase p " +
            "group by p.product order by sum(p.quantity) desc")
    List<Object[]> getTopProductAllTime();
}
