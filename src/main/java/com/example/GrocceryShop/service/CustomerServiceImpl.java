package com.example.GrocceryShop.service;

import com.example.GrocceryShop.interfaces.CustomerService;
import com.example.GrocceryShop.model.Customer;
import com.example.GrocceryShop.model.AppUser;
import com.example.GrocceryShop.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Optional<Customer> getById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public Customer getCustomerByUser(AppUser user) {
        Optional<Customer> customerOpt = customerRepository.findByEmail(user.getUsername());
        if (customerOpt.isPresent()) {
            return customerOpt.get();
        } else {
            Customer customer = new Customer();
            customer.setName(user.getFirstname() + " " + user.getLastname());
            customer.setEmail(user.getUsername());
            customer.setBalance(1000.0);
            return customerRepository.save(customer);
        }
    }
}
