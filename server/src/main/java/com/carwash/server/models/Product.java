package com.carwash.server.models;

import com.carwash.server.models.enums.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String prod_photo;

    private ProductCategory category;

    @ManyToMany(mappedBy = "orderProducts")
    private Set<Order> prods;

    @ManyToMany(mappedBy = "basketProducts")
    private Set<Basket> basks;

    public String getProd_photo() {
        return this.prod_photo;
    }

    public void setProd_photo(String s) {
        this.prod_photo = "http://localhost:8080" + s;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", prod_photo='" + prod_photo + '\'' +
                ", category=" + category +
                '}';
    }
}

