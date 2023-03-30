package com.example.mareu.api;

import static com.example.mareu.api.FakeApiServiceGenerator.generateMeetings;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mareu.model.Meeting;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * Fake implementation of the {@link ApiService} interface used for testing purposes.
 */
public class FakeApiService implements ApiService {

    private final List<Meeting> meetings = generateMeetings();
    private final MutableLiveData<List<Meeting>> meetingsLiveData = new MutableLiveData<>(generateMeetings());

    private long meetingId = 1;

    // Ajoutez un constructeur pour initialiser la liste des réunions
    public FakeApiService() {
    }

    /**
     * Returns a {@link LiveData} of a list of meetings.
     *
     * @return A {@link LiveData} of a list of meetings.
     */
    public LiveData<List<Meeting>> getMeetingsLiveData() {
        return meetingsLiveData;
    }

    /**
     * Adds a new meeting to the list of meetings.
     *
     * @param meeting The meeting to add to the list of meetings.
     */
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
        meeting.setId(meetingId++);
        meetings.add(meeting);

        meetingsLiveData.setValue(meetings);
    }

    /**
     * Deletes a meeting from the list of meetings.
     *
     * @param meetingId The ID of the meeting to delete from the list of meetings.
     */
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

    /**
     * Generates fake meetings and adds them to the list of meetings.
     */
    public void generateFakeMeetings() {
        new Handler(Looper.getMainLooper()).post(() -> {
            for (Meeting meeting : meetings) {
                addMeeting(meeting);
            }
        });
    }
}
