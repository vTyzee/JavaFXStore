// CustomerService.java
package org.example.grocerystore.service;

import org.example.grocerystore.model.entity.Customer;
import org.example.grocerystore.model.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerService {
    public enum ROLES{ CUSTOMER, MANAGER, ADMINISTRATOR }
    private final CustomerRepository repo;
    private Customer currentCustomer;

    public CustomerService(CustomerRepository r) {
        this.repo=r; initSuperUser();
    }

    private void initSuperUser(){
        if(repo.count()>0) return;
        var admin=new Customer();
        admin.setUsername("admin");
        admin.setPassword("12345");
        admin.setFirstname("Super");
        admin.setLastname("Admin");
        admin.setBalance(0.0);
        var rs=admin.getRoles();
        rs.add(ROLES.ADMINISTRATOR.name());
        rs.add(ROLES.MANAGER.name());
        rs.add(ROLES.CUSTOMER.name());
        repo.save(admin);
    }

    public void add(Customer c){
        c.getRoles().clear();
        c.getRoles().add(ROLES.CUSTOMER.name());
        repo.save(c);
    }
    public Customer update(Customer c){ return repo.save(c); }
    public List<Customer> getAllCustomers(){ return repo.findAll(); }
    public Optional<Customer> findById(Long id){ return repo.findById(id); }
    public boolean authenticate(String u,String p){
        var oc=repo.findByUsername(u);
        if(oc.isEmpty()||!oc.get().getPassword().equals(p)) return false;
        currentCustomer=oc.get(); return true;
    }
    public boolean currentUserHasRole(ROLES r){
        return currentCustomer!=null && currentCustomer.getRoles().contains(r.name());
    }
    public boolean currentUserHasAnyRole(ROLES... roles){
        if(currentCustomer==null) return false;
        for(var r:roles) if(currentCustomer.getRoles().contains(r.name())) return true;
        return false;
    }
    public boolean isUsernameTaken(String u){ return repo.findByUsername(u).isPresent(); }
    public void deleteCustomer(Long id){ repo.deleteById(id); }
    public Customer changePassword(Long uid,String np){
        if(!currentUserHasRole(ROLES.ADMINISTRATOR)
                && !Objects.equals(currentCustomer.getId(), uid)){
            throw new SecurityException("Нет прав."); }
        var c = repo.findById(uid).orElseThrow();
        c.setPassword(np); return repo.save(c);
    }
    public Customer getCurrentCustomer(){ return currentCustomer; }
    public void logout(){ currentCustomer=null; }
}
