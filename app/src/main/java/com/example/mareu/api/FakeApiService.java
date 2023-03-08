package com.example.mareu.api;

import static com.example.mareu.api.FakeApiServiceGenerator.generateMeetings;

import com.example.mareu.model.Meeting;

import java.util.List;

public class FakeApiService implements ApiService{

    private List<Meeting> meetings = generateMeetings();

    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }
}
