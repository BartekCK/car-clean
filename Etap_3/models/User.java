package com.carwash.server.models;

import com.carwash.server.models.authority.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Email
    private String email;

    @Column(length = 9)
    private String phone;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @OneToOne(mappedBy = "userId", orphanRemoval = true)
    private Employee employee;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Car> cars;

    @OneToMany(mappedBy = "user")
    private Set<Order> orders;

    @OneToMany(mappedBy = "user")
    private Set<OrderService> orderServices;

    @OneToOne(mappedBy = "user")
    private Opinion opinion;

    @OneToMany(mappedBy = "user")
    private Set<Basket> baskets;

    public User(String username, String password, String email, String phone, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) &&
                username.equals(user.username) &&
                email.equals(user.email) &&
                phone.equals(user.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, phone);
    }
}
