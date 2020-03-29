package com.carwash.server.models;

import com.carwash.server.models.enums.ServiceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Entity
@Table(name = "service")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    //private int car_id;
    //private int user_id;
    //private int employee_id;
    //private int services_id;
    private LocalDateTime date;

    @Enumerated(value = EnumType.STRING)
    private ServiceStatus status;

    @ManyToOne
    @JoinColumn(name = "id")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "id")
    private Service service;
}
