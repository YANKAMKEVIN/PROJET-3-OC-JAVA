package com.example.mareu.api;

import com.example.mareu.model.Meeting;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FakeApiServiceGenerator {
    public static List<Meeting> generateMeetings() {
        List<String> participants = Arrays.asList("participant1@lamzone.fr", "participant2@lamzone.fr", "participant3@lamzone.fr");
        List<Meeting> meetings = new ArrayList<>();

        // Utilisez des données statiques pour générer 10 réunions
        for (int i = 0; i < 10; i++) {
            String name = "REUNION " + (char) ('A' + i);
            LocalTime startTime = LocalTime.of(9 + i, 0);
            LocalDate date = LocalDate.now().plusDays(i);
            String location = "Salle " + (i + 1);
            String subject = "Sujet de la réunion " + (i + 1);
            meetings.add(new Meeting(i, name, startTime, date, location, subject, participants));
        }
        return meetings;
    }
}
