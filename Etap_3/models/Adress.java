package com.carwash.server.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

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
    @JoinColumn(name = "order_id", unique = true)
    private Order orderId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Adress adress = (Adress) o;
        return id == adress.id &&
                orderId.equals(adress.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderId);
    }

    @Override
    public String toString() {
        return "Adress{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", street='" + street + '\'' +
                ", additional='" + additional + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", phone='" + phone + '\'' +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }
}
