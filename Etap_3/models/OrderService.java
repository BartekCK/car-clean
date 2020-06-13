package com.carwash.server.models;

import com.carwash.server.models.enums.OrderServiceStatus;
import com.carwash.server.models.enums.PaidStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "service")
public class OrderService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private LocalDate date;

    private int time;

    @Enumerated(value = EnumType.STRING)
    private PaidStatus paidStatus;

    @Enumerated(value = EnumType.STRING)
    private OrderServiceStatus status;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne()
    @JoinColumn(name = "services_id")
    private Services serviceid;

    @Override
    public String toString() {
        return "OrderService{" +
                "id=" + id +
                ", date=" + date +
                ", time=" + time +
                ", paidStatus=" + paidStatus +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderService that = (OrderService) o;
        return time == that.time &&
                id.equals(that.id) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, time);
    }
}
