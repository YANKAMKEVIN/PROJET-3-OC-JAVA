package com.example.mareu.api;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.mareu.model.Meeting;

import java.util.List;

public interface ApiService {
    LiveData<List<Meeting>> getMeetingsLiveData();
    void deleteMeeting(long meetingId);
    void addMeeting(@NonNull Meeting meeting);
    void generateFakeMeetings();
}
