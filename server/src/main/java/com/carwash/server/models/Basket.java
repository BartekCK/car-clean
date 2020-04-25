package com.carwash.server.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "basket")
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int bill;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "basket_product")
    private List<Product> basketProducts;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @Override
    public String toString() {
        return "Basket{" +
                "id=" + id +
                ", bill=" + bill +
                '}';
    }
}
