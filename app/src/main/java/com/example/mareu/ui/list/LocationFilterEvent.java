package com.example.mareu.ui.list;

public class LocationFilterEvent {
    private String location;

    public LocationFilterEvent(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }
}
