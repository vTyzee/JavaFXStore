package com.example.GrocceryShop.interfaces;

import com.example.GrocceryShop.model.AppUser;
import com.example.GrocceryShop.model.Customer;
import com.example.GrocceryShop.service.AppService;

public interface CustomerService extends AppService<Customer> {
    Customer getCustomerByUser(AppUser user);
}
