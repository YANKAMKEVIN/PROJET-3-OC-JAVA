package com.example.mareu.ui.list;

import java.time.LocalDate;

/**
 * Classe représentant un événement de filtrage par date.
 */
public class DateFilterEvent {
    private LocalDate date;

    /**
     * Construit un nouvel événement de filtrage par date.
     *
     * @param date La date utilisée pour le filtrage.
     */
    public DateFilterEvent(LocalDate date) {
        this.date = date;
    }

    /**
     * Retourne la date utilisée pour le filtrage.
     *
     * @return La date utilisée pour le filtrage.
     */
    public LocalDate getDate() {
        return date;
    }
}
