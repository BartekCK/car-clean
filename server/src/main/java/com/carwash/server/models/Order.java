package com.carwash.server.models;

import com.carwash.server.models.enums.PaidStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int bill;
    //private String car_parameters;
    //@Enumerated(value = EnumType.STRING)
    private PaidStatus paid_status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "order_product")
    private List<Product> orderProducts;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id &&
                bill == order.bill;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bill);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", bill=" + bill +
                ", paid_status=" + paid_status +
                '}';
    }
}
