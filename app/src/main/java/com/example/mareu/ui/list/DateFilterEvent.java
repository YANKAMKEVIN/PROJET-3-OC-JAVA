package com.example.mareu.ui.list;

import java.time.LocalDate;

public class DateFilterEvent {
    private LocalDate date;

    public DateFilterEvent(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }
}
