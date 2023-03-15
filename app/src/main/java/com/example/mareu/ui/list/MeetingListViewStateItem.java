package com.example.mareu.ui.list;

import java.util.List;
import java.util.Objects;


public class MeetingListViewStateItem {


    private long id;
    private String name;
    private List<String> participants;


    public MeetingListViewStateItem(long id, String name, List<String> participants) {
        this.id = id;
        this.name = name;
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
                ", participants=" + participants +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MeetingListViewStateItem)) return false;
        MeetingListViewStateItem that = (MeetingListViewStateItem) o;
        return getId() == that.getId() && getName().equals(that.getName()) && getParticipants().equals(that.getParticipants());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, participants);
    }
}
