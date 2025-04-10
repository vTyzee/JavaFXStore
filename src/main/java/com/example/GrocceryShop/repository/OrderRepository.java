package com.example.GrocceryShop.repository;

import com.example.GrocceryShop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT SUM(o.quantity * o.product.price) FROM Order o WHERE o.orderDate BETWEEN :startDate AND :endDate")
    Double findTotalRevenueBetween(LocalDate startDate, LocalDate endDate);
}
