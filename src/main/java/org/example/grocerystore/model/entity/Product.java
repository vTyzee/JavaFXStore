// Product.java
package org.example.grocerystore.model.entity;

import jakarta.persistence.*;
import java.util.*;

@Entity
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(fetch=FetchType.EAGER)
    private Set<Supplier> suppliers = new HashSet<>();
    private double price;
    private int quantity;
    private int stock;
    @OneToMany(mappedBy="product", cascade=CascadeType.REMOVE)
    private List<Purchase> purchases;

    public Product(){}
    public Long getId(){return id;}
    public void setId(Long i){this.id=i;}
    public String getName(){return name;}
    public void setName(String n){this.name=n;}
    public Set<Supplier> getSuppliers(){return suppliers;}
    public void setSuppliers(Set<Supplier> s){this.suppliers=s;}
    public double getPrice(){return price;}
    public void setPrice(double p){this.price=p;}
    public int getQuantity(){return quantity;}
    public void setQuantity(int q){this.quantity=q;}
    public int getStock(){return stock;}
    public void setStock(int s){this.stock=s;}
}
