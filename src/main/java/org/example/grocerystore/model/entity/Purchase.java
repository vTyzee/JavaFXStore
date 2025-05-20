// Purchase.java
package org.example.grocerystore.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Purchase {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne private Product product;
    @ManyToOne private Customer customer;
    private int quantity;
    private double totalPrice;
    private LocalDateTime purchaseDate;

    public Purchase(){}
    public Purchase(Product p,Customer c,int q,double tp,LocalDateTime pd){
        this.product=p;this.customer=c;this.quantity=q;this.totalPrice=tp;this.purchaseDate=pd;
    }
    // геттеры/сеттеры...
}
