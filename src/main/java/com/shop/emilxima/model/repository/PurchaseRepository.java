package com.shop.emilxima.model.repository;

import com.shop.emilxima.model.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository
        extends JpaRepository<Purchase, Long> {
}
