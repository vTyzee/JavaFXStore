package com.shop.emilxima.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Product product;

    private int quantity;
    private LocalDateTime purchaseDate;

    public Purchase(Customer customer, Product product, int quantity, LocalDateTime purchaseDate) {
        this.customer = customer;
        this.product  = product;
        this.quantity = quantity;
        this.purchaseDate = purchaseDate;
    }
}
