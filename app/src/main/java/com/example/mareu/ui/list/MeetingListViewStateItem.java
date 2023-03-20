package com.example.mareu.ui.list;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;


public class MeetingListViewStateItem {
    private long id;
    private String name;
    private String location;
    private LocalTime startTime;
    private List<String> participants;



    public MeetingListViewStateItem(long id, String name, String location, LocalTime startTime, List<String> participants) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.startTime = startTime;
        this.participants = participants;
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
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MeetingListViewStateItem)) return false;
        MeetingListViewStateItem that = (MeetingListViewStateItem) o;
        return getId() == that.getId() && getName().equals(that.getName()) && getLocation().equals(that.getLocation()) && getStartTime().equals(that.getStartTime()) && getParticipants().equals(that.getParticipants());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getLocation(), getStartTime(), getParticipants());
    }
}
