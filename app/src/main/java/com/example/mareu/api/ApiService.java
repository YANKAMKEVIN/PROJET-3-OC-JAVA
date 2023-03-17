package com.example.mareu.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.mareu.model.Meeting;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ApiService {
    LiveData<List<Meeting>> getMeetingsLiveData();
    void deleteMeeting(long meetingId);
    void addMeeting(
            @NonNull String name,
            @NonNull LocalTime startTime,
            @Nullable LocalDate date,
            @Nullable String location,
            @Nullable String subject,
            @Nullable List<String> participants
    );
    void generateFakeMeetings();
}
