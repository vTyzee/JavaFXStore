package com.example.GrocceryShop.service;

import com.example.GrocceryShop.interfaces.OrderService;
import com.example.GrocceryShop.model.Customer;
import com.example.GrocceryShop.model.Order;
import com.example.GrocceryShop.model.Product;
import com.example.GrocceryShop.repository.CustomerRepository;
import com.example.GrocceryShop.repository.OrderRepository;
import com.example.GrocceryShop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            CustomerRepository customerRepository,
                            ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Order create(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> getById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }


    @Override
    public boolean createOrder(Customer customer, Product product, int quantity) {
        if (product.getQuantity() < quantity) {
            return false;
        }
        double totalCost = product.getPrice() * quantity;
        if (customer.getBalance() < totalCost) {
            return false;
        }
        Order order = new Order(customer, product, quantity);
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
        customer.setBalance(customer.getBalance() - totalCost);
        customerRepository.save(customer);
        orderRepository.save(order);
        return true;
    }


    @Override
    public double calculateRevenueBetween(LocalDate startDate, LocalDate endDate) {
        Double revenue = orderRepository.findTotalRevenueBetween(startDate, endDate);
        return revenue != null ? revenue : 0.0;
    }
}
