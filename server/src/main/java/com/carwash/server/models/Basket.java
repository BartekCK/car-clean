package com.carwash.server.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "basket")
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int bill;

    @ManyToMany
    @JoinTable(name = "basket_product")
    private List<Product> basketProducts;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}