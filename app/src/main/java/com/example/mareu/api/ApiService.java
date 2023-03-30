package com.example.mareu.api;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.mareu.model.Meeting;

import java.util.List;

/**
 * Interface representing the API service for managing meetings.
 * Provides methods for getting a list of meetings, adding a new meeting,
 * deleting an existing meeting, and generating fake meetings for testing purposes.
 */

public interface ApiService {
    LiveData<List<Meeting>> getMeetingsLiveData();
    void deleteMeeting(long meetingId);
    void addMeeting(@NonNull Meeting meeting);
    void generateFakeMeetings();
}
