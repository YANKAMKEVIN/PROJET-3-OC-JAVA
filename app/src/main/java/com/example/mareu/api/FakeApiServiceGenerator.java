package com.example.mareu.api;

import com.example.mareu.model.Meeting;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FakeApiServiceGenerator {

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(FAKE_MEETINGS);
    }
    static ArrayList<String> participants = (ArrayList<String>) Arrays.asList("Participant 1","Participant 2","Participant 3");
    public static List<Meeting> FAKE_MEETINGS = Arrays.asList(
            new Meeting("REUNION A", LocalTime.of(9, 30), LocalTime.of(10, 30), LocalDate.of(2023,3,7),"SALLE 1","C'est une reunion qui consistera a revaloriser les personnes handicape et apres voilà ce qui se fait",participants),
            new Meeting("REUNION B", LocalTime.of(8, 30), LocalTime.of(10, 30),LocalDate.of(2023,3,1),"SALLE 2","C'est une reunion qui consistera a revaloriser les personnes handicape et apres voilà ce qui se fait",participants),
            new Meeting("REUNION C", LocalTime.of(10, 30), LocalTime.of(11, 30),LocalDate.of(2023,3,2),"SALLE 3","C'est une reunion qui consistera a revaloriser les personnes handicape et apres voilà ce qui se fait",participants),
            new Meeting("REUNION D", LocalTime.of(11, 30), LocalTime.of(12, 30),LocalDate.of(2023,3,3),"SALLE 4","C'est une reunion qui consistera a revaloriser les personnes handicape et apres voilà ce qui se fait",participants),
            new Meeting("REUNION E", LocalTime.of(14, 30), LocalTime.of(15, 30),LocalDate.of(2023,3,4),"SALLE 5","C'est une reunion qui consistera a revaloriser les personnes handicape et apres voilà ce qui se fait",participants),
            new Meeting("REUNION F", LocalTime.of(9, 30), LocalTime.of(10, 30),LocalDate.of(2023,3,5),"SALLE 6","C'est une reunion qui consistera a revaloriser les personnes handicape et apres voilà ce qui se fait",participants),
            new Meeting("REUNION G", LocalTime.of(10, 30), LocalTime.of(11, 30),LocalDate.of(2023,3,6),"SALLE 7","C'est une reunion qui consistera a revaloriser les personnes handicape et apres voilà ce qui se fait",participants),
            new Meeting("REUNION H", LocalTime.of(11, 30), LocalTime.of(11, 30),LocalDate.of(2023,3,8),"SALLE 8","C'est une reunion qui consistera a revaloriser les personnes handicape et apres voilà ce qui se fait",participants),
            new Meeting("REUNION I", LocalTime.of(11, 00), LocalTime.of(13, 30),LocalDate.of(2023,3,9),"SALLE 9","C'est une reunion qui consistera a revaloriser les personnes handicape et apres voilà ce qui se fait",participants),
            new Meeting("REUNION J", LocalTime.of(11, 15), LocalTime.of(13, 30),LocalDate.of(2023,3,10),"SALLE 10","C'est une reunion qui consistera a revaloriser les personnes handicape et apres voilà ce qui se fait",participants)
    );
}
