package com.carwash.server.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //private int user_id;
    private int bill;

    @ManyToMany
    @JoinTable(name = "order_product")
    private List<Product> orderProducts;

    @ManyToOne
    @JoinColumn(name = "id")
    private User user;
}
