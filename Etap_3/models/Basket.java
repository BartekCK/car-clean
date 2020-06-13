package com.carwash.server.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Basket basket = (Basket) o;
        return id == basket.id &&
                bill == basket.bill;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bill);
    }

    @Override
    public String toString() {
        return "Basket{" +
                "id=" + id +
                ", bill=" + bill +
                '}';
    }
}
