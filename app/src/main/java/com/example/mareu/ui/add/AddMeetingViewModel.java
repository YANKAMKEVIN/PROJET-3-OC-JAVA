package com.example.mareu.ui.add;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mareu.model.Meeting;
import com.example.mareu.repository.MeetingRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
/**
 * ViewModel pour la fonctionnalité d'ajout de réunion.
 */
public class AddMeetingViewModel extends ViewModel {
    @NonNull
    private final MeetingRepository meetingRepository;
    private final MutableLiveData<List<String>> participantsLiveData = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<String> errorMessageLiveData = new MutableLiveData<>();

    /**
     * Retourne la liste des participants.
     *
     * @return LiveData<List<String>> représentant la liste des participants
     */
    public LiveData<List<String>> getParticipantsLiveData() {
        return participantsLiveData;
    }

    /**
     * Retourne le message d'erreur.
     *
     * @return LiveData<String> représentant le message d'erreur
     */
    public LiveData<String> getErrorMessageLiveData() {
        return errorMessageLiveData;
    }

    /**
     * Ajoute un participant à la liste des participants s'il est valide et si la liste n'est pas pleine.
     *
     * @param newParticipant String représentant l'adresse e-mail du participant à ajouter
     */
    public void addParticipant(String newParticipant) {
        resetErrorMessage();
        List<String> updatedParticipants = participantsLiveData.getValue();
        if (updatedParticipants.size() < 3) {
            if (isValidParticipantEmail(newParticipant)) {
                updatedParticipants.add(newParticipant);
                participantsLiveData.setValue(updatedParticipants);
            } else {
                errorMessageLiveData.setValue("Le nom de participant doit se terminer par '@lamzone.fr'");
            }
        }
        else errorMessageLiveData.setValue("La reunion ne peut pas avoir plus de 3 participants");
    }

    public void resetParticipantsList() {
        participantsLiveData.setValue(new ArrayList<>());
    }


    public void resetErrorMessage() {
        errorMessageLiveData.setValue(null);
    }

    private void setErrorMessage(String errorMessage) {
        errorMessageLiveData.setValue(errorMessage);
    }

    public AddMeetingViewModel(@NonNull MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    public void createAndAddMeeting(String meetingName, LocalDate selectedDate, LocalTime selectedTime, String location, String subject, List<String> participants, MeetingSavedCallback onMeetingAdded) {
        Meeting meeting = new Meeting(meetingName, selectedTime, selectedDate, location, subject, participants);
        onAddButtonClicked(meeting, onMeetingAdded);
    }

    /**
     * Valide les données de la réunion et l'ajoute au repository si toutes les conditions sont remplies.
     *
     * @param meeting Meeting représentant la réunion à ajouter
     * @param callback MeetingSavedCallback qui sera appelé lorsque la réunion est ajoutée avec succès
     */
    @SuppressLint("DefaultLocale")
    public void onAddButtonClicked(
            @NonNull Meeting meeting,
            MeetingSavedCallback callback) {
        resetErrorMessage();
        // Vérifie si les champs obligatoires sont remplis
        if (meeting.getName().trim().isEmpty() || meeting.getParticipants().isEmpty()) {
            setErrorMessage("Veuillez remplir tous les champs obligatoires");
            return;
        }

        // Vérifie si la date sélectionnée est dans le futur
        if (meeting.getDate().isBefore(LocalDate.now())) {
            setErrorMessage("Veuillez sélectionner une date future");
            return;
        }

        // Vérifie si l'heure de début est valide
        LocalTime currentTime = LocalTime.now();
        if (meeting.getDate().isEqual(LocalDate.now()) && meeting.getStartTime().isBefore(currentTime)) {
            setErrorMessage("Veuillez sélectionner une heure de début valide");
            return;
        }

        // Vérifie si le nombre de participants est valide
        final int MAX_PARTICIPANTS = 3;
        if (meeting.getParticipants().size() > MAX_PARTICIPANTS) {
            setErrorMessage(String.format("Le nombre maximum de participants est %d", MAX_PARTICIPANTS));
            return;
        }

        // Vérifie si les adresses e-mail des participants sont valides
        final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        for (String participant : meeting.getParticipants()) {
            if (!participant.matches(EMAIL_REGEX)) {
                setErrorMessage(String.format("L'adresse e-mail \"%s\" n'est pas valide", participant));
                return;
            }
        }

        // Vérifie si l'emplacement sélectionné est disponible pour la date et l'heure sélectionnées
        List<Meeting> meetings = meetingRepository.getMeetingsLiveData().getValue();
        if (meetings != null) {
            for (Meeting meeting1 : meetings) {
                if (meeting1.getLocation().equals(meeting.getLocation()) && meeting1.getDate().equals(meeting.getDate()) && meeting1.getStartTime().equals(meeting.getStartTime())) {
                    setErrorMessage(String.format("L'emplacement \"%s\" n'est pas disponible pour la date et l'heure sélectionnées", meeting.getLocation()));
                    return;
                }
            }
        }

        meetingRepository.addMeeting(meeting);
        // Informez l'activité que la réunion a été enregistrée
        callback.onMeetingSaved();
    }

    public boolean isValidParticipantEmail(String email) {
        return email.endsWith("@lamzone.fr");
    }

    public boolean areAllFieldsFilled(String meetingName, String location, String subject, String datePickerText, String timePickerText, String participantsList) {
        return !location.isEmpty() && !subject.isEmpty() && !meetingName.isEmpty() &&
                !datePickerText.isEmpty() && !timePickerText.isEmpty() && !participantsList.isEmpty();
    }
}
