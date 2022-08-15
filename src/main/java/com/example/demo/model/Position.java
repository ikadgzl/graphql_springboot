package com.example.demo.model;

public enum Position {
    POINT_GUARD("PG"),
    SHOOTING_GUARD("SG"),
    SMALL_FORWARD("SF"),
    POWER_FORWARD("PF"),
    CENTER("C");

    private final String position;

    Position(String position) {
        this.position = position;
    }
}
