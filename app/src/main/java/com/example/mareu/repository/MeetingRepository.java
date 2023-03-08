package com.example.mareu.repository;

import com.example.mareu.api.ApiService;
import com.example.mareu.model.Meeting;

import java.util.List;

public class MeetingRepository {
    private final ApiService apiService;

    public MeetingRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public List<Meeting> getMeetings(){
       return apiService.getMeetings();
    }

    public void deleteMeeting(Meeting meeting){
        apiService.deleteMeeting(meeting);
    }
}
