package com.example.mareu.api;

import static com.example.mareu.api.FakeApiServiceGenerator.generateMeetings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mareu.model.Meeting;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FakeApiService implements ApiService {

    private List<Meeting> meetings = generateMeetings();
    private final MutableLiveData<List<Meeting>> meetingsLiveData = new MutableLiveData<>(new ArrayList<>());

    private long maxId = 0;

    public LiveData<List<Meeting>> getMeetingsLiveData() {
        return meetingsLiveData;
    }

    public void addMeeting(
            @NonNull String name,
            @NonNull LocalTime startTime,
            @Nullable LocalDate date,
            @Nullable String location,
            @Nullable String subject,
            @Nullable List<String> participants

    ) {
        List<Meeting> meetings = meetingsLiveData.getValue();

        if (meetings == null) return;

        meetings.add(
                new Meeting(
                        maxId++,
                        name,
                        startTime,
                        date,
                        location,
                        subject,
                        participants
                )
        );

        meetingsLiveData.setValue(meetings);
    }

    public void deleteMeeting(long meetingId) {
        List<Meeting> meetings = meetingsLiveData.getValue();

        if (meetings == null) return;

        for (Iterator<Meeting> iterator = meetings.iterator(); iterator.hasNext(); ) {
            Meeting meeting = iterator.next();

            if (meeting.getId() == meetingId) {
                iterator.remove();
                break;
            }
        }
        meetingsLiveData.setValue(meetings);
    }

    public void generateFakeMeetings() {
        List<Meeting> fakeMeetings = generateMeetings();
           for (Meeting meeting:fakeMeetings){
            addMeeting(meeting.getName(), meeting.getStartTime(), meeting.getDate(), meeting.getLocation(), meeting.getSubject(), meeting.getParticipants());
        }
    }
}
