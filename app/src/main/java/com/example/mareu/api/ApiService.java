package com.example.mareu.api;

import com.example.mareu.model.Meeting;

import java.util.List;

public interface ApiService {
    List<Meeting> getMeetings();
    void deleteMeeting(Meeting meeting);
}
