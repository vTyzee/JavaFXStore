package com.example.GrocceryShop.interfaces;

import com.example.GrocceryShop.model.Customer;
import com.example.GrocceryShop.model.Order;
import com.example.GrocceryShop.model.Product;
import com.example.GrocceryShop.service.AppService;
import java.time.LocalDate;

public interface OrderService extends AppService<Order> {

    boolean createOrder(Customer customer, Product product, int quantity);
    double calculateRevenueBetween(LocalDate startDate, LocalDate endDate);
}
