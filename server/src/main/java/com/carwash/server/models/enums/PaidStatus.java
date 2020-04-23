package com.carwash.server.models.enums;

public enum PaidStatus {
    NOT_PAID("Nie zapłacono"), PAID("Zapłacono");

    private final String name;
    PaidStatus(String s) {
        name=s;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
