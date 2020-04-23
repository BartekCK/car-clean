package com.carwash.server.models.enums;

public enum OrderServiceStatus {
    WAITING("Rezerwacja"),
    IN_PROGRESS("Zostało 15 min"),
    DONE("Zakończono"),
    CANCEL("Anulowano");

    private final String name;

    OrderServiceStatus(String s) {
        name=s;
    }
    public String toString() {
        return this.name;
    }
}
