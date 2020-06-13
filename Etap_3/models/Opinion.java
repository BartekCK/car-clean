package com.carwash.server.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "opinion")
public class Opinion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int mark;

    private LocalDateTime date;

    private String description;

    private String image;

    @Override
    public String toString() {
        return "Opinion{" +
                "id=" + id +
                ", user=" + user +
                ", mark=" + mark +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Opinion opinion = (Opinion) o;
        return id == opinion.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
