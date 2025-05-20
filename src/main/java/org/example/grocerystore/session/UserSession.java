// UserSession.java
package org.example.grocerystore.session;

import org.example.grocerystore.model.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class UserSession {
    private Customer currentCustomer;
    public Customer getCurrentCustomer(){ return currentCustomer; }
    public void setCurrentCustomer(Customer c){ this.currentCustomer = c; }
}
