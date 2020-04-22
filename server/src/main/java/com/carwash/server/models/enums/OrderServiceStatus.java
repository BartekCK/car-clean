package com.carwash.server.models.enums;

public enum OrderServiceStatus {
    WAITING("Rezerwacja"),
    IN_PROGRESS("Zostało 15 min"),
    DONE("Zakończono"),
    CANCEL("Anulowano");

    OrderServiceStatus(String alert) {

    }
}
