package com.example.mareu.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public class Meeting {


    private int id;
    private String name;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;
    private String location;
    private String subject;
    private List<String> participants;


    public Meeting(int id, String name, LocalTime startTime, LocalTime endTime, LocalDate date, String location, String subject, List<String> participants) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.location = location;
        this.subject = subject;
        this.participants = participants;
    }

    // --- GETTERS AND SETTERS ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Meeting)) return false;
        Meeting meeting = (Meeting) o;
        return getId() == meeting.getId() && getName().equals(meeting.getName()) && getStartTime().equals(meeting.getStartTime()) && getEndTime().equals(meeting.getEndTime()) && getDate().equals(meeting.getDate()) && getLocation().equals(meeting.getLocation()) && getSubject().equals(meeting.getSubject()) && getParticipants().equals(meeting.getParticipants());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, startTime, endTime, date, location, subject, participants);
    }
}
