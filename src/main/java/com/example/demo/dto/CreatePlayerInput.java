package com.example.demo.dto;

import com.example.demo.model.Position;


public class CreatePlayerInput
{
    private String name;
    private String surname;
    private Position position;

    public CreatePlayerInput(String id, String name, String surname, Position position) {
        this.name = name;
        this.surname = surname;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
