package com.example.mareu.ui.add;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mareu.repository.MeetingRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AddMeetingViewModel extends ViewModel {
    @NonNull
    private final MeetingRepository meetingRepository;
    private final MutableLiveData<List<String>> participantsLiveData = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<String> errorMessageLiveData = new MutableLiveData<>();

    public LiveData<List<String>> getParticipantsLiveData() {
        return participantsLiveData;
    }

    public LiveData<String> getErrorMessageLiveData() {
        return errorMessageLiveData;
    }

    public void addParticipant(String newParticipant) {
        if (isValidParticipantEmail(newParticipant)) {
            List<String> updatedParticipants = participantsLiveData.getValue();
            updatedParticipants.add(newParticipant);
            participantsLiveData.setValue(updatedParticipants);
        } else {
            errorMessageLiveData.setValue("Le nom de participant doit se terminer par '@lamzone.fr'");
        }
    }



    public void resetErrorMessage() {
        errorMessageLiveData.setValue(null);
    }

    public AddMeetingViewModel(@NonNull MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    public void onAddButtonClicked(
            @NonNull String name,
            @NonNull LocalTime startTime,
            @NonNull LocalDate date,
            @NonNull String location,
            @NonNull String subject,
            @NonNull List<String> participants,
            MeetingSavedCallback callback) {
        meetingRepository.addMeeting(name, startTime, date, location, subject, participants);
        // Informez l'activité que la réunion a été enregistrée
        callback.onMeetingSaved();
    }

    public boolean isValidParticipantEmail(String email) {
        return email.endsWith("@lamzone.fr");
    }

    public boolean areAllFieldsFilled(String location, String subject, String meetingName, String datePickerText, String timePickerText, String participantsList) {
        return !location.isEmpty() && !subject.isEmpty() && !meetingName.isEmpty() &&
                !datePickerText.isEmpty() && !timePickerText.isEmpty() && !participantsList.isEmpty();
    }
}
