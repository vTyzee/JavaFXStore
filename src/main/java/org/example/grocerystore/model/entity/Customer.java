// Customer.java
package org.example.grocerystore.model.entity;

import jakarta.persistence.*;
import java.util.*;

@Entity
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false, unique=true) private String username;
    private String password, firstname, lastname;
    private double balance;
    @ElementCollection(fetch=FetchType.EAGER) private Set<String> roles = new HashSet<>();

    public Customer(){}
    public Long getId(){return id;}
    public String getUsername(){return username;}
    public void setUsername(String u){this.username=u;}
    public String getPassword(){return password;}
    public void setPassword(String p){this.password=p;}
    public String getFirstname(){return firstname;}
    public void setFirstname(String f){this.firstname=f;}
    public String getLastname(){return lastname;}
    public void setLastname(String l){this.lastname=l;}
    public double getBalance(){return balance;}
    public void setBalance(double b){this.balance=b;}
    public Set<String> getRoles(){return roles;}
    public void setRoles(Set<String> r){this.roles=r;}
}
