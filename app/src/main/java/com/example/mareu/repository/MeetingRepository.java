package com.example.mareu.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.mareu.api.ApiService;
import com.example.mareu.model.Meeting;

import java.util.List;

public class MeetingRepository {
    private final ApiService apiService;

    public MeetingRepository(ApiService apiService) {
        this.apiService = apiService;
        generateFakeMeetings();
    }

    public LiveData<List<Meeting>> getMeetingsLiveData() {
        return apiService.getMeetingsLiveData();
    }

    public void addMeeting(@NonNull Meeting meeting) {apiService.addMeeting(meeting);}

    public void deleteMeeting(long meetingId) {
        apiService.deleteMeeting(meetingId);
    }

    private void generateFakeMeetings() {
        apiService.generateFakeMeetings();
    }
}
