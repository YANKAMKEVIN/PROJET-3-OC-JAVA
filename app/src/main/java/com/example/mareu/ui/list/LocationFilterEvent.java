package com.example.mareu.ui.list;

/**
 * Classe représentant un événement de filtrage par lieu.
 */
public class LocationFilterEvent {
    private String location;

    /**
     * Construit un nouvel événement de filtrage par lieu.
     *
     * @param location Le lieu utilisé pour le filtrage.
     */
    public LocationFilterEvent(String location) {
        this.location = location;
    }


    /**
     * Retourne le lieu utilisé pour le filtrage.
     *
     * @return Le lieu utilisé pour le filtrage.
     */
    public String getLocation() {
        return location;
    }
}
