package com.example.mareu.service;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule; // Importez cette classe
import androidx.lifecycle.MutableLiveData;

import com.example.mareu.api.ApiService;
import com.example.mareu.di.DI;
import com.example.mareu.model.Meeting;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule; // Importez cette classe
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(JUnit4.class)
public class MeetingServiceTest {

    private ApiService service;

    @Rule // Ajoutez cette règle
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private static int ITEMS_COUNT = 10;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void testGetMeetingsLiveData() {
        assertNotNull(service.getMeetingsLiveData().getValue());
    }

    @Test
    public void testAddMeeting() {
        Meeting meeting = new Meeting(11, "Test meeting", LocalTime.of(20, 0), LocalDate.now(), "Salle 11", "Sujet de test", Arrays.asList("test1@lamzone.fr", "test2@lamzone.fr"));
        service.addMeeting(meeting);
        assertTrue(service.getMeetingsLiveData().getValue().contains(meeting));
    }

    @Test
    public void testDeleteMeeting() {
        long meetingIdToDelete = 1;
        List<Meeting> meetingsBeforeDeletion = service.getMeetingsLiveData().getValue();
        assertNotNull(meetingsBeforeDeletion);
        assertTrue(meetingsBeforeDeletion.stream().anyMatch(meeting -> meeting.getId() == meetingIdToDelete));
        service.deleteMeeting(meetingIdToDelete);
        List<Meeting> meetingsAfterDeletion = service.getMeetingsLiveData().getValue();
        assertNotNull(meetingsAfterDeletion);
        assertFalse(meetingsAfterDeletion.stream().anyMatch(meeting -> meeting.getId() == meetingIdToDelete));
        assertTrue(meetingsBeforeDeletion.size()  == meetingsAfterDeletion.size());
    }
    @Test
    public void addMeetingWithEmptyName_shouldNotAddMeeting() {
        // Arrange
        Meeting meeting = new Meeting("", LocalTime.of(14, 0), LocalDate.now(), "Salle A", "Réunion X", new ArrayList<>());
        MutableLiveData<List<Meeting>> meetingsLiveData = (MutableLiveData<List<Meeting>>) service.getMeetingsLiveData();

        // Act
        service.addMeeting(meeting);

        // Assert
        Assert.assertTrue(meetingsLiveData.getValue().size() == ITEMS_COUNT);
    }

    @Test
    public void addMeetingWithPastDate_shouldNotAddMeeting() {
        // Arrange
        Meeting meeting = new Meeting("Réunion Y", LocalTime.of(14, 0), LocalDate.now().minusDays(1), "Salle B", "Sujet Y", new ArrayList<>());
        MutableLiveData<List<Meeting>> meetingsLiveData = (MutableLiveData<List<Meeting>>) service.getMeetingsLiveData();

        // Act
        service.addMeeting(meeting);

        // Assert
        Assert.assertTrue(meetingsLiveData.getValue().size() == ITEMS_COUNT);
    }

    @Test
    public void addMeetingWithInvalidParticipant_shouldNotAddMeeting() {
        // Arrange
        List<String> participants = new ArrayList<>();
        participants.add(null);
        Meeting meeting = new Meeting("Réunion Z", LocalTime.of(14, 0), LocalDate.now().plusDays(1), "Salle C", "Sujet Z", participants);
        MutableLiveData<List<Meeting>> meetingsLiveData = (MutableLiveData<List<Meeting>>) service.getMeetingsLiveData();

        // Act
        service.addMeeting(meeting);

        // Assert
        Assert.assertTrue(meetingsLiveData.getValue().size() == ITEMS_COUNT);
    }

    @Test
    public void addMeetingWithValidData_shouldAddMeeting() {
        // Arrange
        Meeting meeting = new Meeting("Réunion A", LocalTime.of(14, 0), LocalDate.now().plusDays(1), "Salle A", "Sujet A", new ArrayList<>());
        MutableLiveData<List<Meeting>> meetingsLiveData = (MutableLiveData<List<Meeting>>) service.getMeetingsLiveData();

        // Act
        service.addMeeting(meeting);

        // Assert
        Assert.assertFalse(meetingsLiveData.getValue().isEmpty());
        Assert.assertTrue(meetingsLiveData.getValue().size() == ITEMS_COUNT);

        //Assert.assertEquals(meetingsLiveData.getValue().get(0), meeting);
    }

}
