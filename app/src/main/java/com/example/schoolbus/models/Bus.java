package com.example.schoolbus.models;

public class Bus {

    private String name;
    private String type;
    private String plate;
    private String username;
    private String picture;

    public Bus(String pict, String name, String type, String plate, String username) {
        this.name = name;
        this.type = type;
        this.plate = plate;
        this.picture = pict;
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}


