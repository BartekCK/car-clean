package com.carwash.server.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private String email;
    private String phone_number;

    @OneToMany(mappedBy = "id")
    private Set<Car> cars;

    @OneToOne(mappedBy = "user_id")
    private Employee employee;
}
