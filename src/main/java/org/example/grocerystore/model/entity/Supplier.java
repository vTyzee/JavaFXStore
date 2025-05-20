// Supplier.java
package org.example.grocerystore.model.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
public class Supplier implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String contact;
    @ManyToMany(mappedBy="suppliers") private Set<Product> products = new HashSet<>();
    public Supplier(){}
    public Long getId(){return id;}
    public void setId(Long i){this.id=i;}
    public String getName(){return name;}
    public void setName(String n){this.name=n;}
    public String getContact(){return contact;}
    public void setContact(String c){this.contact=c;}
    public Set<Product> getProducts(){return products;}
    public void setProducts(Set<Product> p){this.products=p;}
}
