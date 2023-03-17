package com.example.mareu.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.mareu.api.ApiService;
import com.example.mareu.model.Meeting;

import java.time.LocalDate;
import java.time.LocalTime;
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

    public void addMeeting(
            @NonNull String name,
            @NonNull LocalTime startTime,
            @Nullable LocalDate date,
            @Nullable String location,
            @Nullable String subject,
            @Nullable List<String> participants

    ) {
        apiService.addMeeting(name, startTime, date, location, subject, participants);
    }

    public void deleteMeeting(long meetingId) {
        apiService.deleteMeeting(meetingId);
    }

    private void generateFakeMeetings() {
        apiService.generateFakeMeetings();
    }
}
