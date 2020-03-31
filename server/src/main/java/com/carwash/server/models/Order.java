package com.carwash.server.models;

import com.carwash.server.models.enums.PaidStatus;
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
    private String car_parameters;
    private PaidStatus paid_status;

    @ManyToMany
    @JoinTable(name = "order_product")
    private List<Product> orderProducts;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
