package com.carwash.server.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "services")
public class Services {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String name;

    private int price;

    @Column(unique = true)
    private String description;

    @Column(unique = true)
    private String image;

    @OneToMany(mappedBy = "serviceid")
    private Set<Service> service;

    public void setImage(String s) {
        this.image = "http://localhost:8080" + s;
    }
}
