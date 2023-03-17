package com.example.mareu.api;

import com.example.mareu.model.Meeting;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FakeApiServiceGenerator {
    public static List<Meeting> generateMeetings() {
        List<String> participants = Arrays.asList("participant1@mareu.com", "participant2@mareu.com", "participant3@mareu.com");
        List<Meeting> meetings = new ArrayList<>();

        // Génère 10 réunions aléatoires
        for (int i = 0; i < 10; i++) {
            String name = "REUNION " + (char) ('A' + i);
            LocalTime startTime = LocalTime.of((int) (Math.random() * 24), (int) (Math.random() * 60));
            LocalDate date = LocalDate.now().plusDays((int) (Math.random() * 30));
            String location = "SALLE " + (int) (Math.random() * 10);
            String subject = "C'est une réunion qui consiste à revaloriser les personnes handicapées et après voilà ce qui se fait";
            meetings.add(new Meeting(i, name, startTime, date, location, subject, participants));
        }
        return meetings;
    }
}
