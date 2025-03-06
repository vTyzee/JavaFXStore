package com.shop.emilxima.model.service;

import com.shop.emilxima.model.entity.Customer;
import com.shop.emilxima.model.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepo;

    public CustomerService(CustomerRepository customerRepo) {
        this.customerRepo = customerRepo;
    }

    /**
     * Добавляет нового покупателя (или можешь сделать edit)
     */
    public void addCustomer(String firstName, String lastName, double balance) {
        Customer c = new Customer(firstName, lastName, balance);
        customerRepo.save(c);
    }

    // Здесь можешь добавлять методы editCustomer(...), findAll(), и т.д.
}
