package com.example.mareu.ui.list;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
/**
 * La classe MeetingListViewStateItem représente l'état d'une réunion dans la vue de liste.
 * Elle contient les informations de base d'une réunion, telles que l'ID, le nom, l'emplacement,
 * l'heure de début, les participants et la date.
 */
public class MeetingListViewStateItem {
    private long id;
    private String name;
    private String location;
    private LocalTime startTime;
    private List<String> participants;
    private LocalDate date;

    /**
     * Construit un nouvel objet MeetingListViewStateItem avec les informations de réunion spécifiées.
     *
     * @param id          l'ID de la réunion
     * @param name        le nom de la réunion
     * @param location    l'emplacement de la réunion
     * @param startTime   l'heure de début de la réunion
     * @param participants la liste des participants à la réunion
     * @param date        la date de la réunion
     */
    public MeetingListViewStateItem(long id, String name, String location, LocalTime startTime, List<String> participants, LocalDate date) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.startTime = startTime;
        this.participants = participants;
        this.date = date;
    }


    // --- GETTERS AND SETTERS ---

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
    public List<String> getParticipants() {
        return participants;
    }
    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    @Override
    public String toString() {
        return "MeetingListViewStateItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", startTime=" + startTime +
                ", participants=" + participants +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MeetingListViewStateItem)) return false;
        MeetingListViewStateItem that = (MeetingListViewStateItem) o;
        return getId() == that.getId() && getName().equals(that.getName()) && getLocation().equals(that.getLocation()) && getStartTime().equals(that.getStartTime()) && getParticipants().equals(that.getParticipants()) && getDate().equals(that.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getLocation(), getStartTime(), getParticipants(), getDate());
    }
}
