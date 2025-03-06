package com.shop.emilxima.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity) {
        this.name   = name;
        this.price  = price;
        this.quantity = quantity;
    }
}
