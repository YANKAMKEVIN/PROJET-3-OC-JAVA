package com.example.mareu.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mareu.model.Meeting;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class MeetingRepository {

    private final MutableLiveData<List<Meeting>> meetingsLiveData = new MutableLiveData<>(new ArrayList<>());

    private long maxId = 0;

    public MeetingRepository() {
        generateFakeMeetings();
    }

    public void addMeeting(
            @NonNull String name,
            @NonNull LocalTime startTime,
            @Nullable LocalTime endTime,
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
                        endTime,
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

    public LiveData<List<Meeting>> getMeetingsLiveData() {
        return meetingsLiveData;
    }

    private void generateFakeMeetings() {
        List<String> participants = Arrays.asList("Participant 1", "Participant 2", "Participant 3");
        addMeeting("REUNION A", LocalTime.of(9, 30), LocalTime.of(10, 30), LocalDate.of(2023, 3, 7), "SALLE 1", "C'est une reunion qui consistera a revaloriser les personnes handicape et apres voilà ce qui se fait", participants);
        addMeeting("REUNION B", LocalTime.of(8, 30), LocalTime.of(10, 30), LocalDate.of(2023, 3, 1), "SALLE 2", "C'est une reunion qui consistera a revaloriser les personnes handicape et apres voilà ce qui se fait", participants);
        addMeeting("REUNION C", LocalTime.of(10, 30), LocalTime.of(11, 30), LocalDate.of(2023, 3, 2), "SALLE 3", "C'est une reunion qui consistera a revaloriser les personnes handicape et apres voilà ce qui se fait", participants);
        addMeeting("REUNION D", LocalTime.of(11, 30), LocalTime.of(12, 30), LocalDate.of(2023, 3, 3), "SALLE 4", "C'est une reunion qui consistera a revaloriser les personnes handicape et apres voilà ce qui se fait", participants);
        addMeeting("REUNION E", LocalTime.of(14, 30), LocalTime.of(15, 30), LocalDate.of(2023, 3, 4), "SALLE 5", "C'est une reunion qui consistera a revaloriser les personnes handicape et apres voilà ce qui se fait", participants);
        addMeeting("REUNION F", LocalTime.of(9, 30), LocalTime.of(10, 30), LocalDate.of(2023, 3, 5), "SALLE 6", "C'est une reunion qui consistera a revaloriser les personnes handicape et apres voilà ce qui se fait", participants);
        addMeeting("REUNION G", LocalTime.of(10, 30), LocalTime.of(11, 30), LocalDate.of(2023, 3, 6), "SALLE 7", "C'est une reunion qui consistera a revaloriser les personnes handicape et apres voilà ce qui se fait", participants);
        addMeeting("REUNION H", LocalTime.of(11, 30), LocalTime.of(11, 30), LocalDate.of(2023, 3, 8), "SALLE 8", "C'est une reunion qui consistera a revaloriser les personnes handicape et apres voilà ce qui se fait", participants);
        addMeeting("REUNION I", LocalTime.of(11, 00), LocalTime.of(13, 30), LocalDate.of(2023, 3, 9), "SALLE 9", "C'est une reunion qui consistera a revaloriser les personnes handicape et apres voilà ce qui se fait", participants);
        addMeeting("REUNION J", LocalTime.of(11, 15), LocalTime.of(13, 30), LocalDate.of(2023, 3, 10), "SALLE 10", "C'est une reunion qui consistera a revaloriser les personnes handicape et apres voilà ce qui se fait", participants);
    }
}
