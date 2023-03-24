package com.example.mareu.api;

import static com.example.mareu.api.FakeApiServiceGenerator.generateMeetings;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mareu.model.Meeting;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FakeApiService implements ApiService {

    private final List<Meeting> meetings = generateMeetings();
    private final MutableLiveData<List<Meeting>> meetingsLiveData = new MutableLiveData<>(new ArrayList<>());

    private long maxId = 0;

    public LiveData<List<Meeting>> getMeetingsLiveData() {
        return meetingsLiveData;
    }

    public void addMeeting(@NonNull Meeting meeting) {
        // Vérification de la validité des paramètres
        if (meeting.getName().trim().isEmpty()) {
            return;
        }

        // Vérification de la date
        if (meeting.getDate() != null && meeting.getDate().isBefore(LocalDate.now())) {
            return;
        }

        // Vérification de la validité des participants
        if (meeting.getParticipants() != null) {
            List<String> validParticipants = new ArrayList<>();
            for (String participant : meeting.getParticipants()) {
                if (participant != null && !participant.trim().isEmpty()) {
                    validParticipants.add(participant.trim());
                }
            }
            if (validParticipants.isEmpty()) {
                return;
            }
            meeting.setParticipants(validParticipants);
        }

        List<Meeting> meetings = meetingsLiveData.getValue();
        if (meetings == null) {
            return;
        }

        // Affecter un nouvel ID au meeting avant de l'ajouter à la liste
        meeting.setId(maxId++);
        meetings.add(meeting);

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
           for (Meeting meeting:meetings){
            addMeeting(meeting);
        }
    }
}
