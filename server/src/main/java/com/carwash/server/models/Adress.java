package com.carwash.server.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "adresses")
public class Adress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String surname;
    private String street;
    private String additional;
    private String city;
    private String postalCode;
    private String phone;
    private String countryCode;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order orderId;
}
